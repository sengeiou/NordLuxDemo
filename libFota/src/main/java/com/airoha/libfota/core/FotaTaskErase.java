package com.airoha.libfota.core;

import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.util.ByteHelper;
import com.airoha.libfota.constant.ErrorCodeManager;
import com.airoha.libfota.constant.FlashAddress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FotaTaskErase extends FotaTask {
    private static final int HEX_64K = 0x10000;
    private static final int HEX_32K = 0x8000;
    private static final int HEX_4K = 0x1000;

    private FlashAddress mFlashAddress;

    private int mAreaNum;

    public FotaTaskErase(AirohaOtaMgr mgr, int areaNum, AirohaLink airohaLink, int slidingWindow) {
        super(mgr, airohaLink, slidingWindow);

        mFlashAddress = mgr.getmFlashAddress();

        mAreaNum = areaNum;

        mCmdsMap = genCmdMapFromList(generateErashFlashCmds(mAreaNum));
    }

    @Override
    public void handleResp(byte[] resp) {

        if(resp.length != 6)
            return;

        if(resp[0] != AirohaOtaCmd.CMD_ERASE_FLASH)
            return;

        mOtaMgr.stopRespTimer();

        String strRespAddr = ByteHelper.toHex(Arrays.copyOfRange(resp, 1,5));
        CmdItem cmdItem = mCmdsMap.get(strRespAddr);

        if(resp[5] == ErrorCodeManager.RES_SUCCESS){

            Log.d(TAG, "erase resp packet: " + ByteHelper.toHex(resp));

            cmdItem.isRespSuccess = true;

            mCmdsMap.remove(cmdItem); // reduce map size

            pollCmdItem();
        }else {
            boolean canRetry = retry();

            if(!canRetry)
                mOtaMgr.getEventListener().OnStatusError(resp[5], mErrorCodeManager.getErrorMsg(resp[5]));
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
                Log.d(TAG, "isAllResp, next startProgramArea");

                return true;
            }else {
                Log.d(TAG, "startRespTimer");
                mOtaMgr.startRespTimer();
            }
        }

        return false;
    }

    private List<byte[]> generateErashFlashCmds(int area){
        List<byte[]> list = new ArrayList<>();

        int addrStart = mFlashAddress.getFlashArea1Start() + FlashAddress.getCmdAddrOffset();
        int addrEnd = mFlashAddress.getFlashArea1End() + FlashAddress.getCmdAddrOffset();
        // Area 2 starts with 0x48000
        if(area == 0x02){
            addrStart = mFlashAddress.getFlashArea2Start() + FlashAddress.getCmdAddrOffset();
            addrEnd = mFlashAddress.getFlashArea2End() + FlashAddress.getCmdAddrOffset();
        }

        while (addrStart < addrEnd){
            byte[] addrLittle = ByteHelper.intToBytes(addrStart); // 4 bytes little endia
            byte eraseType = getEraseLengthCmd(addrStart, addrEnd);

            //        Command format: ---- 02 @@ @@ @@ @@ ##
            //        @@ @@ @@ @@: Flash Address, need length-aligned
            //        ##: erase length --- (0: 64KB, 1: 32KB, 2: 4KB, other: invalid)
            byte[] listitem = new byte[6];
            // 0
            listitem[0] = AirohaOtaCmd.CMD_ERASE_FLASH;
            // 1~4
            System.arraycopy(addrLittle, 0, listitem, 1, 4);
            // 5
            listitem[5] = eraseType;
            // done
            list.add(listitem);

            int offset = getNextEraseAddrOffset(addrStart, addrEnd);
            if(offset == 0)
                break;

            addrStart = addrStart + offset;
        }
        return list;
    }

    private static byte getEraseLengthCmd(int start, int end) {
        int rangeCount = end - start+1;

        final byte CMD_ERASE_64K = 0x00;
        if(start % HEX_64K == 0 && rangeCount >= HEX_64K)
            return CMD_ERASE_64K;

        final byte CMD_ERASE_32K = 0x01;
        if(start % HEX_32K == 0 && rangeCount >= HEX_32K)
            return CMD_ERASE_32K;

        final byte CMD_ERASE_4K = 0x02;
        if(start % HEX_4K == 0 && rangeCount >= HEX_4K)
            return CMD_ERASE_4K;

        // minimum is 4K
        return CMD_ERASE_4K;
    }

    private static int getNextEraseAddrOffset(int start, int end) {
        int rangeCount = end - start+1;

        if(start % HEX_64K == 0 && rangeCount >= HEX_64K)
            return HEX_64K;

        if(start % HEX_32K == 0 && rangeCount >= HEX_32K)
            return HEX_32K;

        if(start % HEX_4K == 0 && rangeCount >= HEX_4K)
            return HEX_4K;
        return 0;
    }
}
