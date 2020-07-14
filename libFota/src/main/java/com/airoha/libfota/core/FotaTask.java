package com.airoha.libfota.core;

import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.util.ByteHelper;
import com.airoha.libfota.constant.ErrorCodeManager;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Daniel.Lee on 2016/9/1.
 */
class FotaTask {
    protected static String TAG = "FotaTask";

    protected final AirohaLink mAirohaLink;

    protected int mSlidingWindow =0;

    protected Queue<CmdItem> mCmdsList;

    protected LinkedHashMap<String, CmdItem> mCmdsMap;

    protected final AirohaOtaMgr mOtaMgr;

    protected static final int RETRY_LIMIT = 3;

    protected long mProgramStartTime;

    protected ErrorCodeManager mErrorCodeManager = new ErrorCodeManager();

    public FotaTask(AirohaOtaMgr mgr, LinkedHashMap<String, CmdItem> cmdsMap, AirohaLink airohaLink, int slidingWindow) {
        TAG = this.getClass().getSimpleName();

        mOtaMgr = mgr;

        mAirohaLink = airohaLink;
        mSlidingWindow = slidingWindow;

        mCmdsList = new LinkedList<>();

        mCmdsMap = cmdsMap;

        for(CmdItem cmdItem : mCmdsMap.values()){
            if(!cmdItem.isRespSuccess){
                mCmdsList.add(cmdItem);
            }
        }
    }

    public FotaTask(AirohaOtaMgr mgr, AirohaLink airohaLink, int slidingWindow) {
        TAG = this.getClass().getSimpleName();

        mOtaMgr = mgr;

        mAirohaLink = airohaLink;
        mSlidingWindow = slidingWindow;
    }


    public boolean retry(){
        mCmdsList.clear();

        for(CmdItem cmdItem : mCmdsMap.values()){
            if(!cmdItem.isRespSuccess){
                if(cmdItem.RetryCount < RETRY_LIMIT){
                    cmdItem.RetryCount++;
                    Log.d(TAG, "retrying: " + ByteHelper.toHex(cmdItem.Cmd));
                    mCmdsList.add(cmdItem);
                }else {
                    return false; // failed to retry
                }
            }
        }

        pollCmdItem();

        return true;
    }


    public void pollCmdItem(){
        //            byte[] cmd = mCmds.get(i);

        if(mCmdsList.size() == 0)
            return;

        CmdItem cmdItem = mCmdsList.poll();

        cmdItem.isSend = true;

        mOtaMgr.writeCharc(cmdItem.Cmd);

        mOtaMgr.startRespTimer();
    }

    public void startPrePoll(){
        mCmdsList = new LinkedList<>();

        for(CmdItem cmdItem : mCmdsMap.values()){
            if(!cmdItem.isRespSuccess){
                mCmdsList.add(cmdItem);
            }
        }

        mProgramStartTime = System.currentTimeMillis();

        for(int i = 0; i < mSlidingWindow; i++){
            pollCmdItem();
        }
    }

    public boolean isAllRespSuccess(){

        return true;
    }

    public void handleResp(byte[] resp) {

    }

    protected LinkedHashMap<String, CmdItem> genCmdMapFromList(List<byte[]> CmdList) {
        LinkedHashMap<String, CmdItem> map = new LinkedHashMap<>();

        for(byte[] cmdItem : CmdList) {
            String keyAddr = ByteHelper.toHex(Arrays.copyOfRange(cmdItem, 1, 5));

            CmdItem item = new CmdItem();
            item.Cmd = cmdItem;

            map.put(keyAddr, item);
        }

        return map;
    }
}
