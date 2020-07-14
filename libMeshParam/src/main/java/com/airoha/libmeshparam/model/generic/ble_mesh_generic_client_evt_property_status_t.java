package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*!  Message parameter of generic property status */

public class ble_mesh_generic_client_evt_property_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of property ID. */
    public short property_id;
    /**< The value of access type. */
    public byte access;
    /**< The value of the specified property id. Need to be freed. */
    public byte[] property_value;
    /**< The length of the value of the specified property id. */
    public short property_value_length;
}
