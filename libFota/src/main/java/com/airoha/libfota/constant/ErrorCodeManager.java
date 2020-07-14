package com.airoha.libfota.constant;

/**
 * Created by MTK60279 on 2017/10/30.
 */

public class ErrorCodeManager {
    public static final byte RES_SUCCESS = 0x00;

    // 2017.10.24 only one byte 0xFF to indicate OTA function is disabled
    public static final byte RES_FAIL = (byte) 0xFF;

    public static final byte RES_DEVICE_NO_RESET_AFTER_OTA = (byte) 0x55;
    public static final byte RES_FLASH_ADDR_OUT_OTA_RANGE = (byte) 0x56;
    public static final byte RES_GET_UNKNOWN_FLASH_ERASE_TYPE = (byte) 0x78;
    public static final byte RES_FLASH_ADDR_NO_ALIGN = (byte) 0x87;

    public static final String STR_DEVICE_NO_RESET_AFTER_OTA = "Device doesn't reset after OTA.";
    public static final String STR_FLASH_ADDR_OUT_OTA_RANGE = "Flash address is out of valid range.";
    public static final String STR_GET_UNKNOWN_FLASH_ERASE_TYPE = "Type of flash erase is unknown.";
    public static final String STR_FLASH_ADDR_NO_ALIGN = "Flash address doesn't align.";
    public static final String STR_PROGRAM_FAIL = "Flash program fail.";

    public String getErrorMsg(byte errorCode)
    {
        String rtn;
        switch (errorCode) {
            case RES_DEVICE_NO_RESET_AFTER_OTA:
                rtn = STR_DEVICE_NO_RESET_AFTER_OTA;
                break;
            case RES_FLASH_ADDR_OUT_OTA_RANGE:
                rtn = STR_FLASH_ADDR_OUT_OTA_RANGE;
                break;
            case RES_GET_UNKNOWN_FLASH_ERASE_TYPE:
                rtn = STR_GET_UNKNOWN_FLASH_ERASE_TYPE;
                break;
            case RES_FLASH_ADDR_NO_ALIGN:
                rtn = STR_FLASH_ADDR_NO_ALIGN;
                break;
            default:
                rtn = STR_PROGRAM_FAIL;
                break;
        }
        return rtn;
    }
}
