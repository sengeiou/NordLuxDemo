package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*! Message parameter of generic battery status  */

public class ble_mesh_generic_client_evt_battery_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Generic Battery Level state. */
    public int battery_level;
    /**< The value of the Generic Battery Time to Discharge state. */
    public int time_to_discharge;
    /**< The value of the Generic Battery Time to Charge state. */
    public int time_to_charge;
    /**< The value of the Generic Battery presence. */
    public int flags_presence;
    /**< The value of the Generic Battery indicator. */
    public int flags_indicator;
    /**< The value of the Generic Battery charging. */
    public int flags_charging;
    /**< The value of the Generic Battery serviceablity. */
    public int flags_serviceability;
}
