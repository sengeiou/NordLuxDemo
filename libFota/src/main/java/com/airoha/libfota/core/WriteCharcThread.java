package com.airoha.libfota.core;

import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.util.ByteHelper;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Daniel.Lee on 2016/9/1.
 */
class WriteCharcThread { //extends Thread {
    private static final String TAG = "WriteCharcThead";

    private final AirohaLink mAirohaLink;

    private int mSlidingWindow =0;

//    private Queue<byte[]> mSlicedPacketQueue = new LinkedList<>();

    private Queue<CmdItem> mCmdsList;

    private LinkedHashMap<String, CmdItem> mCmdsMap;

    private final AirohaOtaMgr mOtaMgr;

    private static final int RETRY_LIMIT = 3;

    public WriteCharcThread(AirohaOtaMgr mgr, LinkedHashMap<String, CmdItem> cmdsMap, AirohaLink airohaLink, int slidingWindow) {
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
        for(int i = 0; i < mSlidingWindow; i++){
            pollCmdItem();
        }
    }


    public void handleOnGattWrite(){

    }

//    public void stop(){
//        synchronized (mCmdsList){
//            mCmdsList.clear();
//        }
//    }

}
