package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_LPN_POLL_TIMEOUT_STATUS. */

public class config_client_evt_lpn_poll_timeout_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< The unicast address of the Low Power node. */
    public short lpn_address;
    /**< The current value of the PollTimeout timer of the Low Power node. */
    public int poll_timeout;
    /**< Reserved for future use. */
    public int rfu;
}
