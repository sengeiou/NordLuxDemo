package com.airoha.libmeshparam;

/**
 * Constant value .
 */

public class DEFINE {
    final static int BLE_MESH_KEY_SIZE = 16;
    final static int BLE_MESH_NETKEY_SIZE= BLE_MESH_KEY_SIZE;            /**< The network key size. */
    final static int BLE_MESH_APPKEY_SIZE= BLE_MESH_KEY_SIZE;            /**< The application key size. */
    final static int BLE_MESH_DEVKEY_SIZE= BLE_MESH_KEY_SIZE;            /**< The device key size. */
    final static int BLE_MESH_SESSIONKEY_SIZE= BLE_MESH_KEY_SIZE;        /**< The session key size. */
    final static int BLE_MESH_CONFIRMATION_KEY_SIZE= BLE_MESH_KEY_SIZE;  /**< The confirmation key size. */
    final static int BLE_MESH_PUBLIC_KEY_SIZE = 64;                       /**< The public key size. */
    final static int BLE_MESH_BEACONKEY_SIZE= BLE_MESH_KEY_SIZE;         /**< The beacon key size. */

    final static int BLE_MESH_TTL_MAX = 0x7F;

    final static int BLE_MESH_MODEL_GENERIC_ONOFF_GET = 0x8201;
    final static int BLE_MESH_MODEL_GENERIC_ONOFF_SET = 0x8202;
    final static int BLE_MESH_MODEL_GENERIC_ONOFF_SET_UNACKNOWLEDGED = 0x8203;
    final static int BLE_MESH_MODEL_GENERIC_ONOFF_STATUS = 0x8204;
}
