package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*!  Message parameter of generic power last status  */

public class ble_mesh_generic_client_evt_power_last_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Generic Power Last state. */
    public short power;
}
