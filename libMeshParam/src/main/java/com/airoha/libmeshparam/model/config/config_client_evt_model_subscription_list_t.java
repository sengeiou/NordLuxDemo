package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_MODEL_SUBSCRIPTION_LIST. */

public class config_client_evt_model_subscription_list_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status code for the requesting message. */
    public byte status;
    /**< Address of the element. */
    public short element_address;
    /**< Model id. */
    public int model_id;
    /**< All addresses from the Subscription List of an element. */
    public short[] address;
    /**< Total count of address. */
    public short address_count;
}
