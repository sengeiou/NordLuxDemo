package com.airoha.libmeshparam.model.config;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_NETWORK_TRANSMIT_STATUS. */

public class config_client_evt_network_transmit_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Number of transmissions for each Network PDU originating from the node. */
    public byte retransmit_count;
    /**< Number of 10-millisecond steps between retransmissions. */
    public byte retransmit_interval_steps;
}
