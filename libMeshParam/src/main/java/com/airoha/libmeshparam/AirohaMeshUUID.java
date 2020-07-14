package com.airoha.libmeshparam;

/**
 * Service/Characteristic UUID for Mesh provision and configuration.
 */

public class AirohaMeshUUID {

    private static final int MESH_UUID_LENGTH = 16;
    /**
     * {@code
     * Airoha Mesh Provision  service/characteristic UUID.
     * service UUID = 00001827-0000-1000-8000-00805F9B34FB
     * }
     * */
    public static final String MESH_PROVISION_SERVICE_UUID = "00001827-0000-1000-8000-00805F9B34FB";
    /**
     * {@code
     * Characteristic1 UUID = 00002ADB-0000-1000-8000-00805F9B34FB.
     * For provision, app can send mesh command to device via this characteristic.
     * Property supports write/write without response.
     * }
     */
    public static final String MESH_PROVISION_SERVICE_DATA_IN_UUID = "00002ADB-0000-1000-8000-00805F9B34FB";
    /**
     * {@code
     * Characteristic2 UUID = 00002ADC-0000-1000-8000-00805F9B34FB.
     * App can be notified the mesh command response via this characteristic.
     * Property supports notify.
     * }
     */
    public static final String MESH_PROVISION_SERVICE_DATA_OUT_UUID = "00002ADC-0000-1000-8000-00805F9B34FB";

    /**
     * {@code
     * Airoha Mesh Provision  service/characteristic UUID.
     * service UUID = 00001828-0000-1000-8000-00805F9B34FB.
     * }
     * */
    public static final String MESH_PROXY_SERVICE_UUID = "00001828-0000-1000-8000-00805F9B34FB";
    /**
     * {@code
     * Characteristic1 UUID = 00002ADD-0000-1000-8000-00805F9B34FB.
     * For configuration, app can send mesh command to device via this characteristic.
     * Property supports write/write without response.
     * }
     */
    public static final String MESH_PROXY_SERVICE_DATA_IN_UUID = "00002ADD-0000-1000-8000-00805F9B34FB";
    /**
     * {@code
     * Characteristic2 UUID = 00002ADE-0000-1000-8000-00805F9B34FB.
     * App can be notified the mesh command response via this characteristic.
     * Property supports notify.
     * }
     */
    public static final String MESH_PROXY_SERVICE_DATA_OUT_UUID = "00002ADE-0000-1000-8000-00805F9B34FB";
}
