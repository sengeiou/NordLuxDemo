package com.airoha.libfota.core;

import android.util.Log;

import com.airoha.btdlib.util.ByteHelper;
import com.google.common.io.BaseEncoding;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Daniel.Lee on 2016/8/29.
 */
class AirohaOtaWAResolver {

    private final static String TAG = "AirohaOtaWAResolver";

    /**
     * Response format:   01 ## [$$ @@ @@ @@ @@ ++ ++ ++ ++][%% ** ** ** ** -- -- -- --]
     */
    private final static byte START_BYTE = (byte) 0x01;

    /**
     * ## = 目前正在執行的Code Area ------- (1 or 2)
     */
    public byte mWorkingArea = (byte) 0x00;

    /**
     * $$ = Code Area1 狀態---------------(0:valid, other value: invalid)
     */
    private byte mStateArea1 = (byte)0xFF;

    /**
     * %% = Code Area2 狀態---------------(0:valid, other value: invalid)
     */
    private byte mStateArea2 = (byte)0xFF;

    /**
     *  4bytes hex, Code Area1 revision number (僅在Code Area1 valid時有意義)
     */
    private final byte[] mRevNumArea1 = new byte[4];

    /**
     *  4bytes hex, Code Area2 revision number (僅在Code Area2 valid時有意義)
     */
    private final byte[] mRevNumArea2 = new byte[4];

    /**
     *  ++ ++ ++ ++  = Code Area1 Checksum (僅在Code Area1 valid時有意義)
     */
    private final byte[] mChkSumArea1 = new byte[4];

    /**
     * -- -- -- --  = Code Area2 Checksum (僅在Code Area2 valid時有意義)
     */
    private final byte[] mChkSumArea2 = new byte[4];


    public AirohaOtaWAResolver(byte[] blepacket){
        try {
            parser(blepacket);
        }
        catch (Exception e){
            Log.d(TAG, " parsing exception:" + e.getMessage());
        }

    }

    private void parser(byte[] blepacket){
        // check length = 20
        if(blepacket==null || blepacket.length!=20)
            return;

        if(blepacket[0]!=START_BYTE)
            return;

        mWorkingArea = blepacket[1];
        Log.d(TAG, "Flash Working Area:" + mWorkingArea);

        // area 1
        mStateArea1 = blepacket[2];
        Log.d(TAG, "Flash Area 1 state:" + mStateArea1);
        System.arraycopy(blepacket, 3, mRevNumArea1, 0, 4);
        ArrayUtils.reverse(mRevNumArea1);
        Log.d(TAG, "Flash Area 1 rev:" + BaseEncoding.base16().encode(mRevNumArea1));
        System.arraycopy(blepacket, 7, mChkSumArea1, 0, 4);
        Log.d(TAG, "Flash Area 1 chksum:" + BaseEncoding.base16().encode(mChkSumArea1));

        // area 2
        mStateArea2 = blepacket[11];
        Log.d(TAG, "Flash Area 2 state:" + mStateArea2);
        System.arraycopy(blepacket, 12, mRevNumArea2, 0, 4);
        ArrayUtils.reverse(mRevNumArea2);
        Log.d(TAG, "Flash Area 2 rev:" + BaseEncoding.base16().encode(mRevNumArea2));
        System.arraycopy(blepacket, 16, mChkSumArea2, 0, 4);
        Log.d(TAG, "Flash Area 2 chksum:" + BaseEncoding.base16().encode(mChkSumArea2));
    }

    public String getArea1Rev(){
        return BaseEncoding.base16().encode(mRevNumArea1);
    }

    public String getArea2Rev(){
        return BaseEncoding.base16().encode(mRevNumArea2);
    }

    public boolean getArea1Stat(){
        return mStateArea1 == 0x00;
    }

    public boolean getArea2Stat(){
        return mStateArea2 == 0x00 ;
    }

    public long getLongArea1Rev() {
        return ByteHelper.bytesToLong(mRevNumArea1);
    }

    public long getLongArea2Rev() {
        return ByteHelper.bytesToLong(mRevNumArea2);
    }
}
