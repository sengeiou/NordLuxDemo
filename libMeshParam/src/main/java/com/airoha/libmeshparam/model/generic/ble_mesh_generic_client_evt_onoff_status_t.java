package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Message parameter of generic onoff status.
 */

public class ble_mesh_generic_client_evt_onoff_status_t extends ble_mesh_base_status_t implements Serializable{
    /** Indicate the present value of the Generic OnOff state. */
    public byte present_onoff;
    /** Indicate the target value of the Generic OnOff state (optional). */
    public byte target_onoff;
    /** Indicate the remaining time of transition. */
    public byte remaining_time;
}
