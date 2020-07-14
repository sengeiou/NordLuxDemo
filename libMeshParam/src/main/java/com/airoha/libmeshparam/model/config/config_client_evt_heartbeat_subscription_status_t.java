package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_HEARTBEAT_SUBSCRIPTION_STATUS. */

public class config_client_evt_heartbeat_subscription_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Status code for the requesting message. */
    public byte status;
    /**< Source address for Heartbeat message. */
    public short source;
    /**< Destination address for Heartbeat messages. */
    public short destination;
    /**< Remaining Period for processing Heartbeat messages. */
    public byte period_log;
    /**< Number of Heartbeat messages remaining to be sent. */
    public byte count_log;
    /**< Minimum hops when receiving Heartbeat messages. */
    public byte min_hops;
    /**< Maximum hops when receiving Heartbeat messages. */
    public byte max_hops;
}
