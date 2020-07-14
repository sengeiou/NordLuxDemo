package com.airoha.btdlib.constant;

/**
 * Created by Daniel.Lee on 2016/8/29.
 */
public class AirohaOtaUUID {

    /**
     * {@code
     * Airoha OTA  service/characteristic UUID.
     * service UUID = 4169726F-6861-4446-5553-657276696365.
     * }
     * */
    public static final String DEFAULT_OTA_SERVICE_UUID = "4169726F-6861-4446-5553-657276696365";
    /**
     * {@code
     * Characteristic1 UUID = 4169726F-6861-4446-5543-6F6D6D616E64.
     * app can send command to device via this characteristic.
     * Property supports write/write without response.
     * }
     */
    public static final String DEFAULT_OTA_WRITE_CHARC_UUID = "4169726F-6861-4446-5543-6F6D6D616E64";
    /**
     * {@code
     * Characteristic2 UUID = 4169726F-6861-4446-5543-6D6452657370.
     * App can be notified the command response or the status of flash writing via this characteristic.
     * Property supports notify.
     * }
     */
    public static final String DEFAULT_OTA_NOTIFY_CHARC_UUID = "4169726F-6861-4446-5543-6D6452657370";

}
