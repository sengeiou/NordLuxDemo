package com.airoha.libmeshparam.prov;

/**
 * Event parameter of mesh event @ref BLE_MESH_EVT_PROV_DONE
 */

public class ble_mesh_evt_prov_done_t {
    /** Indicate the target unicast address. */
    public short address;
    /** Indicate the device key. */
    public byte[] device_key;
    /** Indicate the provisioning process is successful or not. */
    public boolean success;
}
