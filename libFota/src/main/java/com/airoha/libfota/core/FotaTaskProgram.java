package com.airoha.libfota.core;

import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.util.ByteHelper;
import com.airoha.libfota.constant.ErrorCodeManager;
import com.airoha.libfota.constant.FlashAddress;

import java.util.ArrayList;
import java.util.Arrays;

import static com.airoha.libfota.core.AirohaOtaMgr.getAddressInt;

public class FotaTaskProgram extends FotaTask {
    private int mTotalResps;
    private int mTotalCmds;
    private int mProgramDataLength;

    private FlashAddress mFlashAddress;
    private byte[] mBinArea1Data;
    private byte[] mBinArea2Data;
    private byte[] mBinArea1CheckSum;
    private byte[] mBinArea2CheckSum;

    private int mAreaNum;

    public FotaTaskProgram(AirohaOtaMgr mgr, int areaNum, AirohaLink airohaLink, int slidingWindow) {
        super(mgr, airohaLink, slidingWindow);

        mBinArea1Data = mgr.getmBinArea1Data();
        mBinArea2Data = mgr.getmBinArea2Data();
        mBinArea1CheckSum = mgr.getmBinArea1CheckSum();
        mBinArea2CheckSum = mgr.getmBinArea2CheckSum();

        mAreaNum = areaNum;

        mFlashAddress = mgr.getmFlashAddress();

        mCmdsMap = genCmdMapFromList(generateProramCmds(mAreaNum));

        mTotalCmds = mCmdsMap.size();
    }


    private int getCmdMaxDataLength() {
        return mOtaMgr.getCmdMaxDataLength();
    }

    @Override
    public void handleResp(byte[] resp) {
        if(resp.length != 6)
            return;

        if(resp[0] != AirohaOtaCmd.CMD_DATA_PROGRAM)
            return;

        mOtaMgr.stopRespTimer();

        String strRespAddr = ByteHelper.toHex(Arrays.copyOfRange(resp, 1,5));
        CmdItem cmdItem = mCmdsMap.get(strRespAddr);

        if(resp[5] == ErrorCodeManager.RES_SUCCESS){

            Log.d(TAG, "handleProgramResp addr:" + strRespAddr);

            cmdItem.isRespSuccess = true;

            mCmdsMap.remove(cmdItem); // reduce map size

            mTotalResps++;
            Log.d(TAG, String.format("resp progress %d / %d", mTotalResps, mTotalCmds));

            float progress = (float) mTotalResps/ (float) mTotalCmds;

            if(progress > 1.0f){
                progress = 1.0f;
            }

            String proStr = String.format("%.1f", progress*100);
            Log.d(TAG, "format progress: " + proStr);

            mOtaMgr.getEventListener().OnUpdateProgrammingProgress(progress);

            pollCmdItem();
        }else {
            Log.d(TAG, "recovering addr:" + strRespAddr);

            boolean canRetry = retry();

            if(!canRetry)
                mOtaMgr.getEventListener().OnStatusError(resp[5], mErrorCodeManager.getErrorMsg(resp[5]));

            return;
        }
    }

    @Override
    public boolean isAllRespSuccess() {
        boolean isAllSend = true;
        for(CmdItem ct : mCmdsMap.values()){
            if(!ct.isSend){
                isAllSend = false;
                break;
            }
        }

        if(isAllSend){

            boolean isAllResp = true;
            for(CmdItem ct : mCmdsMap.values()){
                if(!ct.isRespSuccess){
                    isAllResp = false;
                    break;
                }
            }

            if(isAllResp){

                Log.d(TAG, "Step 2, finish program");

                float throughPut = (float) ((mProgramDataLength /1024) /       // kB
                        ((System.currentTimeMillis() - mProgramStartTime) / 1000.0));  // sec

                mOtaMgr.getEventListener().OnReportProgramThroughput(throughPut);

                Log.d(TAG, "isAllResp, next changeWorkingArea");

                return true;
            }else {
                Log.d(TAG, "startRespTimer");
                mOtaMgr.startRespTimer();
            }
        }

        return false;
    }

    private ArrayList<byte[]> generateProramCmds(int areaNum){
        final byte[] dataToProgram;
        final int startAddr;
        final byte[] checkSum;
        switch (areaNum){
            case 1:
                dataToProgram = mBinArea1Data;
                startAddr = mFlashAddress.getFlashArea1Start() + FlashAddress.getCmdAddrOffset();
                checkSum = mBinArea1CheckSum;
                break;
            case 2:
                dataToProgram = mBinArea2Data;
                startAddr = mFlashAddress.getFlashArea2Start() + FlashAddress.getCmdAddrOffset();
                checkSum = mBinArea2CheckSum;
                break;
            default: // using area 2 as defauult
                dataToProgram = mBinArea2Data;
                startAddr = mFlashAddress.getFlashArea2Start() + FlashAddress.getCmdAddrOffset();
                checkSum = mBinArea2CheckSum;
                break;
        }


        ArrayList<byte[]> list = new ArrayList<>();

        mProgramDataLength = dataToProgram.length;

        int quotient = dataToProgram.length/ getCmdMaxDataLength();

        Log.d(TAG, "Bin data length /CmdMaxDataLength = " + quotient);

        int remainder = dataToProgram.length% getCmdMaxDataLength();

        Log.d(TAG, "Bin data length %CmdMaxDataLength = " + remainder);

        for(int i = 0; i<quotient;i++){
            byte[] listItem = new byte[6+getCmdMaxDataLength()]; // Android BLE MTU

            // 0
            listItem[0] = AirohaOtaCmd.CMD_DATA_PROGRAM;
            int addNow = startAddr + i* getCmdMaxDataLength();
            Log.d(TAG, "addNow: " + addNow);
            // 1~4 addr
            byte[] addr = ByteHelper.intToBytes(addNow);
            Log.d(TAG, "addNowHex:" + ByteHelper.toHex(addr));
            System.arraycopy(addr, 0, listItem, 1, 4);
            // 5
            listItem[5] = (byte) getCmdMaxDataLength();
            // data length-------(0: 256 byte, other: 1~255byte)
            if(getCmdMaxDataLength() == 256){
                listItem[5] = 0x00;
            }

            // 6~
            System.arraycopy(dataToProgram, i* getCmdMaxDataLength(), listItem, 6, getCmdMaxDataLength());

            list.add(listItem);
        }

        if(remainder != 0){
            byte[] listItem = new byte[6+remainder];

            // 0
            listItem[0] = AirohaOtaCmd.CMD_DATA_PROGRAM;
            int lastBinIdx = quotient* getCmdMaxDataLength();
            int addNow = startAddr + lastBinIdx;
            Log.d(TAG, "addNow: " + addNow);
            // 1~4
            byte[] addr = ByteHelper.intToBytes(addNow);
            Log.d(TAG, "addNowHex:" + ByteHelper.toHex(addr));
            System.arraycopy(addr, 0, listItem, 1, 4);
            // 5
            listItem[5] = (byte) remainder;
            // 6~end
            System.arraycopy(dataToProgram, lastBinIdx, listItem, 6, remainder);

            Log.d(TAG, "full list time(red): " + ByteHelper.toHex(listItem));
            list.add(listItem);
        }

        // add check sum
        // get the last idx
        byte[] checkSumItem = new byte[6+4];
        // 0
        checkSumItem[0] = AirohaOtaCmd.CMD_DATA_PROGRAM;
        // 1~4
        byte[] lastItem = list.get(list.size()-1);
        int lastoffset = lastItem[5];
        int lastaddr = getAddressInt(lastItem);
        int addNow = lastaddr+lastoffset;
        Log.d(TAG, "addNow: " + addNow);
        byte[] addr = ByteHelper.intToBytes(addNow);
        Log.d(TAG, "addNowHex: " + addNow);
        System.arraycopy(addr, 0, checkSumItem, 1, 4);
        //5
        checkSumItem[5] = 4;
        // 6~9
        System.arraycopy(checkSum, 0, checkSumItem, 6, 4);
        // final
        list.add(checkSumItem);

        return list;
    }
}
