package com.airoha.libmeshparam.model.config;


import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_GATT_PROXY_STATUS. */

public class config_client_evt_gatt_proxy_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< GATT proxy state. */
    public byte gatt_proxy;
}
