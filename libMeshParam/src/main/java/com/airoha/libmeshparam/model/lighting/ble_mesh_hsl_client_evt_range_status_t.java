package com.airoha.libmeshparam.model.lighting;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of HSL client model event @ref BLE_MESH_LIGHT_HSL_CLIENT_EVT_RANGE_STATUS. */

public class ble_mesh_hsl_client_evt_range_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Hue Range Min field of the Light HSL Hue Range state. */
    public byte status_code;
    /**< The value of the Hue Range Min field of the Light HSL Hue Range state. */
    public short hue_min;
    /**< The value of the Hue Range Max field of the Light HSL Hue Range state. */
    public short hue_max;
    /**< The value of the Saturation Range Min field of the Light HSL Saturation Range state. */
    public short saturation_min;
    /**< The value of the Saturation Range Max field of the Light HSL Saturation Range state. */
    public short saturation_max;
}
