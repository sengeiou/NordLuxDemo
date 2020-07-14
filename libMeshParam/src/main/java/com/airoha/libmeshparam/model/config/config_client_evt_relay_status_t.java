package com.airoha.libmeshparam.model.config;


import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/** Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_RELAY_STATUS. */

public class config_client_evt_relay_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Relay state. */
    public byte relay;
    /**< Number of retransmissions on advertising bearer for each Network PDU relayed by the node. */
    public byte retransmit_count;
    /**< Number of 10-millisecond steps between retransmissions. */
    public byte retransmit_interval_steps;
}
