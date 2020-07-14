package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*! Message parameter of generic power range status */
public class ble_mesh_generic_client_evt_power_range_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status Code for the requesting message. */
    public byte status_code;
    /**< The value of the Generic Power Min field of the Generic Power Range state. */
    public short range_min;
    /**< The value of the Generic Power Max field of the Generic Power Range state. */
    public short range_max;
}
