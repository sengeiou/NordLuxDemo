package com.airoha.libmeshparam.model.lighting;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of HSL client model event @ref BLE_MESH_LIGHT_HSL_CLIENT_EVT_DEFAULT_STATUS. */

public class ble_mesh_hsl_client_evt_default_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Light Lightness Default state. */
    public short lightness;
    /**< The value of the Light Hue Default state. */
    public short hue;
    /**< The value of the Light Saturation Default state.*/
    public short saturation;
}
