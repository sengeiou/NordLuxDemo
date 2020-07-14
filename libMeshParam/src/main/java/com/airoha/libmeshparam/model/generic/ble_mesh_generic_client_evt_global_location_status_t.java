package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*!    Message parameter of generic global location status  */

public class ble_mesh_generic_client_evt_global_location_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value of the Generic Location Latitude state. */
    public  int latitude;
    /**< The value of the Generic Location Longitude state. */
    public  int longitude;
    /**< The value of the Generic Location Altitude state. */
    public  short altitude;
}
