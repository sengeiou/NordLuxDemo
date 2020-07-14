package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*!  Message parameter of default transition time status */

public class ble_mesh_generic_client_evt_default_transition_time_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Generic Default Transition Time state. */
    public byte transition_time;
}
