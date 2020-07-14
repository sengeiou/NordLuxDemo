package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_MODEL_APP_LIST. */

public class config_client_evt_model_app_list_t extends ble_mesh_base_status_t implements Serializable {
    /**< Status code for the requesting message. */
    public byte status;
    /**< Address of the element. */
    public short element_address;
    /**< Model id. */
    public int model_id;
    /**< All addresses from the Subscription List of an element. */
    public short[] appkey_indexes;
    /**< Total count of appkey_indexes. */
    public int appkey_count;
}
