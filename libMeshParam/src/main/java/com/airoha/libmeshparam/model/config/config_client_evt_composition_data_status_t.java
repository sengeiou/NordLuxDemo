package com.airoha.libmeshparam.model.config;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_COMPOSITION_DATA_STATUS.
 */

public class config_client_evt_composition_data_status_t extends ble_mesh_base_status_t implements Serializable{
    /**Page number of the Composition Data. */
    public byte page;
    /**Composition Data for the identified page. */
    public ble_mesh_composition_data_t composition_data;
}
