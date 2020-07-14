package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_HEARTBEAT_PUBLICATION_STATUS. */

public class config_client_evt_heartbeat_publication_status_t extends ble_mesh_base_status_t implements Serializable {
    /**< Status code for the requesting message. */
    public byte status;
    /**< Destination address for Heartbeat messages. */
    public short destination;
    /**< Number of Heartbeat messages remaining to be sent. */
    public byte count_log;
    /**< Period for sending Heartbeat messages. */
    public byte period_log;
    /**< TTL to be used when sending Heartbeat messages. */
    public byte ttl;
    /**< Bit field indicating features that trigger Heartbeat messages when changed. */
    public short features;
    /**< Index of the NetKey. */
    public short netkey_index;
}
