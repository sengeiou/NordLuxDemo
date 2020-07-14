package com.airoha.libmeshparam.model.lighting;


import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of lightness client model event @ref BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_RANGE_STATUS. */

public class ble_mesh_lightness_client_evt_range_status_t extends ble_mesh_base_status_t implements Serializable{
    public byte status_code;    /**< Status Code for the requesting message. */
    public short range_min;     /**< The value of the Lightness Range Min field of the Light Lightness Range state. */
    public short range_max;     /**< The value of the Lightness Range Max field of the Light Lightness Range state. */
}
