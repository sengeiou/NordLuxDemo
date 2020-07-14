package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*! Message parameter of generic power level status */

public class ble_mesh_generic_client_evt_power_level_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Indicate the present value of the Generic Power Actual state. */
    public short present_power;
    /**< Indicate the target value of the Generic Power Actual state (Optional). */
    public short target_power;
    /**< Indicate the remaining time of transition. */
    public byte remaining_time;
}
