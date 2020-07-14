package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_APPKEY_LIST. */

public class config_client_evt_appkey_list_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status code for the requesting message. */
    public byte status;
    /**< Index of the NetKey. */
    public short netkey_index;
    /**< A list of AppKey Indexes known to the node. */
    public short[] appkey_indexes;
    /**< The count of appkey_indexes. */
    public int appkey_count;
}
