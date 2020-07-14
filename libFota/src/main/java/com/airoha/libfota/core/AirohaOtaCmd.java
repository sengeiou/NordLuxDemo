package com.airoha.libfota.core;

/**
 * Created by Daniel.Lee on 2017/1/13.
 */

public class AirohaOtaCmd {
    /**
     * APP order 'read status command' to Characteristic1
     * Command format:   01
     */
    public static final byte CMD_READ_STATUS = 0x01;
    public static final byte CMD_ERASE_FLASH = 0x02;
    public static final byte CMD_DATA_PROGRAM = 0x03;
    public static final byte CMD_CHANGE_WORKING_AREA = 0x04;

    public static final byte CMD_CHECK_BOOTCODE_VER = 0x06;

    public static final byte CMD_CHECK_CODE_AREA_ADDR = 0x07;

    // 2018.08.30 Daniel new cmd
    public static final byte CMD_APPLY_NEW_FW = 0x09; // not used for this branch

    //Read MT7682 status RSP(9 Bytes,  AB1613 -> App)
    //{
    //        uint8_t     command;
    //        uint32_t    fireware_revision;
    //        uint32_t    area2_checksum;
    //}
    public static final byte DFU_OTA_READ_MT7682_STATUS = 0x0A;

    //MT7682 FOTA Format (App -> AB1613)
    //typedef struct
    //{
    //        uint8_t     opcode; //COMBO_DFU_OTA_MT7682_FOTA_PAYLOAD=0x0B
    //        uint8_t     command;// command = 0xA3
    //        uint8_t     payload_crc8; //Calc crc8 with (addr + length + payload)
    //        uint32_t   address; //fota data offset
    //        uint16_t   payload_length; // length
    //        uint8_t  payload_start[1];
    //} __attribute__((packed)) mt7682_FOTA_cmd_pkt;

    //MT7682 FOTA Format (AB1613 -> App)
    // typedef struct
    //  {
    //        uint8_t     command;  // DFU_OTA_MT7682_FOTA_PAYLOAD
    //        uint32_t    address; //fota data offset
    //        uint8_t     status; // 0:  data ok; 1: crc error, fail
    //  }__attribute__((packed))rsp_stru;
    public static final byte DFU_OTA_MT7682_FOTA_PAYLOAD = 0x0B;

    //MT7682 FOTA BEGIN CMD:   (2 Bytes,  App -> AB1613)
    // typedef struct
    //  {
    //        uint8_t     command;  //  COMBO_DFU_OTA_MT7682_FOTA_BEGIN
    //        uint8_t     status; //default  = 0;
    //  }__attribute__((packed)) mt7682_FOTA_begin_cmd;

    //MT7682 FOTA BEGIN RSP:   (2 Bytes, AB1613 -> App)
    // typedef struct
    //  {
    //        uint8_t     command; //  COMBO_DFU_OTA_MT7682_FOTA_BEGIN
    //        uint8_t     status; //default  = 0:  Begin ok,   1: something wrong, OTA stop;
    //  }__attribute__((packed)) mt7682_FOTA_begin_cmd;
    public static final byte DFU_OTA_MT7682_FOTA_BEGIN = 0x0C;

    //MT7682 FOTA FINISH CMD:   (2 Bytes,  App -> AB1613)
    // typedef struct
    //  {
    //        uint8_t     command;  //  COMBO_DFU_OTA_MT7682_FOTA_DATA_FINISH
    //        uint8_t     status; //default  = 0;
    //  }__attribute__((packed)) mt7682_FOTA_trans_finish_cmd;

    //MT7682 FOTA FINISH RSP:   (2 Bytes, AB1613 -> App)
    // typedef struct
    //  {
    //        uint8_t     command; // COMBO_DFU_OTA_MT7682_FOTA_DATA_FINISH
    //        uint8_t     status; //default  = 0:  FOTA Download Done,   1: something wrong, OTA stop;
    //  }__attribute__((packed)) mt7682_FOTA_trans_finish_resp;
    public static final byte DFU_OTA_MT7682_FOTA_DATA_FINISH = 0x0D;

    //Reboot MT7682 CMD:   (2 Bytes,  App -> AB1613)
    // typedef struct
    //  {
    //        uint8_t     command;  // DFU_OTA_MT7682_REBOOT
    //        uint8_t     status; //default  = 0;
    //  }__attribute__((packed))cmd_stru;

    //Reboot MT7682 RSP:   (2 Bytes, AB1613 -> App)
    // typedef struct
    //  {
    //        uint8_t     command;  // DFU_OTA_MT7682_REBOOT
    //        uint8_t     status; //default  = 0:  reboot ok,   1: reboot fail;
    //  }__attribute__((packed))rsp_stru;
    public static final byte DFU_OTA_MT7682_REBOOT = 0x0E;


    //DFU_OTA_READ_STATUS = 1,
    //    DFU_OTA_ERASE_FLASH,
    //    DFU_OTA_WRITE_FLASH,
    //    DFU_OTA_CHANGE_AREA,
    //    DFU_OTA_CLEAR_CACHE_READ_STATUS,
    //    DFU_OTA_READ_BOOT_CODE_VER,
    //    DFU_OTA_READ_FLASH_INFO,
    //    DFU_OTA_READ_MT7682_STATUS,
    //    DFU_OTA_MT7682_FOTA_PAYLOAD,
    //    DFU_OTA_MT7682_FOTA_DATA_FINISH,
    //    DFU_OTA_MT7682_REBOOT,
    //    DFU_OTA_UNKNOWN_CMD = 0xFF,


    // MT7682 FOTA Total Page CMD:   (5 Bytes,  App -> AB1613)
    // typedef struct
    //  {
    //        uint8_t     command;  // COMBO_DFU_OTA_MT7682_TOTAL_PAGE
    //        uint32_t   total_page; //
    //  }__attribute__((packed)) mt7682_FOTA_total_page_cmd;
    public static final byte DFU_OTA_MT7682_TOTAL_PAGE = 0x0F;


}
