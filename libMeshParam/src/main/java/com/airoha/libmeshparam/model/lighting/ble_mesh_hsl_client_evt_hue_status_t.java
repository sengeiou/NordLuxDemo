package com.airoha.libmeshparam.model.lighting;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of HSL client model event @ref BLE_MESH_LIGHT_HSL_CLIENT_EVT_HUE_STATUS. */

public class ble_mesh_hsl_client_evt_hue_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The present value of the Light HSL Hue state. */
    public short present_hue;
    /**< The target value of the Light HSL Hue state. (Optional)*/
    public short target_hue;
    /**< The remaining time. Valid when target_hue exists.*/
    public byte remaining_time;
}
