package com.airoha.libmeshparam.model.generic;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/* Message parameter of generic level status*/
public class ble_mesh_generic_client_evt_level_status_t extends ble_mesh_base_status_t implements Serializable {
    /**< Indicate the present value of the Generic Level state. */
    public short present_level;
    /**< Indicate the target value of the Generic Level state (Optional). */
    public short target_level;
    /**< Indicate the remaining time of transition. */
    public byte remaining_time;
}
