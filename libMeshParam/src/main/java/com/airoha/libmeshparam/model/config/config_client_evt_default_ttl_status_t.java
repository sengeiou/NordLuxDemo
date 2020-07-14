package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_DEFAULT_TTL_STATUS. */

public class config_client_evt_default_ttl_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Default TTL value. */
    byte ttl;
}
