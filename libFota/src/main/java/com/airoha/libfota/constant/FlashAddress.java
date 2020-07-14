package com.airoha.libfota.constant;

import com.airoha.libfota.constant.ProductFlag;

/**
 * Created by Daniel.Lee on 2017/2/21.
 */

public class FlashAddress {

    //private int mAreaCapacity = 0x1D000;
    private int mFlashArea1Start; // = 0x6000;
    private int mFlashArea1End; // = mFlashArea1Start + mAreaCapacity - 0x01;
    private int mFlashArea2Start; // = 0x23000;
    private int mFlashArea2End; //= mFlashArea2Start + mAreaCapacity - 0x01;

    private static final int CMD_ADDR_OFFSET = 0x80000;

    public FlashAddress(int area1Start, int area2Start, int area1Length, int area2Length){
        mFlashArea1Start = area1Start;
        mFlashArea2Start = area2Start;
        mFlashArea1End = area1Start + area1Length - 0x01;
        mFlashArea2End = area2Start + area2Length - 0x01;
    }

    public static int getCmdAddrOffset() {
        if(ProductFlag.BuildFor161X){
            return 0x200000;
        }else {
            return 0x80000;
        }
    }

    public int getFlashArea1Start(){
        return mFlashArea1Start;
    }

    public int getFlashArea2Start(){
        return mFlashArea2Start;
    }

    public int getFlashArea1End(){
        return mFlashArea1End;
    }

    public int getFlashArea2End(){
        return mFlashArea2End;
    }


}
