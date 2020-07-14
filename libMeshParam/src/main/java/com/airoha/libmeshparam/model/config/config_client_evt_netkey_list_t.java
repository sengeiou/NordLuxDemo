package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_NETKEY_LIST. */

public class config_client_evt_netkey_list_t extends ble_mesh_base_status_t implements Serializable{
    /**< A list of NetKey Indexes known to the node. */
    public short[] netkey_indexes;
    /**< The count of netkey_indexes. */
    public int netkey_count;
}
