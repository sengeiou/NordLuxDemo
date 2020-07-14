package com.airoha.libmeshparam.model.generic;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/*! Message parameter of generic local location status */

public class ble_mesh_generic_client_evt_local_location_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The value describes the North coordinate of the device using a local coordinate system. */
    public short north;
    /**< The value describes the East coordinate of the device using a local coordinate system. */
    public short east;
    /**< The value determines the altitude of the device relative to the generic location global altitude. */
    public short altitude;
    /**< The value describes the floor number where the element is installed. */
    public byte floor_number;
    /**< The value describes the uncertianty of the location information the element exposes. */
    public short uncertainty;
}
