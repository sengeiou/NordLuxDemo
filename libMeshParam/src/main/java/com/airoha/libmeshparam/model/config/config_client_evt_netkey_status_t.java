package com.airoha.libmeshparam.model.config;


import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_NETKEY_STATUS. */

public class config_client_evt_netkey_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status code for the requesting message. */
    public byte status;
    /**< Index of the NetKey. */
    public short netkey_index;
}
