package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*! Message parameter of generic properties status */

public class ble_mesh_generic_client_evt_properties_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Generic Properties state. Need to be freed. */
    public   short[] property_ids;
    /**< The total number of property ids. */
    public   short property_ids_length;
}
