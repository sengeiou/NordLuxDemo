package com.airoha.libfota.constant;

import com.airoha.libfota.constant.ProductFlag;

/**
 * Created by MTK60279 on 2017/10/30.
 */

public class BinAddrs {
    public static final int BIN_BOOTCODE_START_ADDR = 0x003F8;
    public static final int BIN_CODE_AREA_1_ADDR = 0x25E7;
    public static  final int BIN_CODE_AREA_2_ADDR = 0x25F0;
    //private static  final int BIN_CODE_ARERA_ADDR_OFFSET = 0x80000;

    public static int getBinCodeAreraAddrOffset() {
        if(ProductFlag.BuildFor161X){
            return 0x200000;
        }else {
            return 0x80000;
        }
    }
}
