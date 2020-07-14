package com.airoha.libmeshparam.model.lighting;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of HSL client model event @ref BLE_MESH_LIGHT_HSL_CLIENT_EVT_STATUS and @ref BLE_MESH_LIGHT_HSL_CLIENT_EVT_TARGET_STATUS.
 */

public class ble_mesh_hsl_client_evt_status_t extends ble_mesh_base_status_t implements Serializable {
    /** The value of the Light HSL Lightness state. */
    public short lightness;
    /** The value of the Light HSL Hue state. */
    public short hue;
    /** The value of the Light HSL Saturation state. */
    public short saturation;
    /** The remaining time. (Optional)*/
    public byte remaining_time;
}
