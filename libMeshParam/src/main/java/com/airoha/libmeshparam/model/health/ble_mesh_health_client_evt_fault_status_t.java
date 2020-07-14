package com.airoha.libmeshparam.model.health;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of health client model event @ref BLE_MESH_HEALTH_CLIENT_EVT_FAULT_STATUS. */

public class ble_mesh_health_client_evt_fault_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Identifier of a most recently performed test. */
    public byte test_id;
    /**< 16-bit Bluetooth assigned Company Identifier. */
    public short company_id;
    /**< An array contains a sequence of 1-octet fault values. */
    public byte[] fault_array;
    /**< Length of the fault array. */
    public byte fault_array_length;
}
