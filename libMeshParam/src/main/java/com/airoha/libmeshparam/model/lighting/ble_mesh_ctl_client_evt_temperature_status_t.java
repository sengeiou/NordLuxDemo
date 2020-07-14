package com.airoha.libmeshparam.model.lighting;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of CTL client model event @ref BLE_MESH_LIGHT_CTL_CLIENT_EVT_TEMPERATURE_STATUS. */

public class ble_mesh_ctl_client_evt_temperature_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The present value of the Light CTL Temperature state. */
    public short present_ctl_temperature;
    /**< The present value of the Light CTL Delta UV state. */
    public short present_ctl_delta_uv;
    /**< The target value of the Light CTL Temperature state (Optional) */
    public short target_ctl_temperature;
    /**< The target value of the Light CTL Delta UV state (Optional) */
    public short target_ctl_delta_uv;
    /**< The remaining time. */
    public byte remaining_time;
}
