package com.airoha.libmeshparam.model.lighting;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of lightness client model event @ref BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_LIGHTNESS_STATUS.
 */

public class ble_mesh_lightness_client_evt_status_t extends ble_mesh_base_status_t implements Serializable{
    /** The present value of the Light Lightness Actual state. */
    public short present_lightness;
    /** The present value of the Light Lightness Actual state. */
    public short target_lightness;
    /** The remaining time. */
    public byte remaining_time;
}
