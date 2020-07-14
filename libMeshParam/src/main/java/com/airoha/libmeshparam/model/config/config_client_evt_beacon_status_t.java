package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 *  Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_BEACON_STATUS.
 */

public class config_client_evt_beacon_status_t extends ble_mesh_base_status_t implements Serializable{
    public byte beacon;            /**< Secure Network Beacon state. */
}
