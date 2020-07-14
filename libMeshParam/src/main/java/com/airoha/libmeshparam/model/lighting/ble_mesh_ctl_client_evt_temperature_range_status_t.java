package com.airoha.libmeshparam.model.lighting;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of CTL client model event @ref BLE_MESH_LIGHT_CTL_CLIENT_EVT_TEMPERATURE_RANGE_STATUS. */

public class ble_mesh_ctl_client_evt_temperature_range_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status Code for the requesting message. */
    public byte status_code;
    /**< The value of the Temperature Range Min field of the Light CTL Temperature Range state. */
    public short range_min;
    /**< The value of the Temperature Range Max field of the Light CTL Temperature Range state. */
    public short range_max;
}
