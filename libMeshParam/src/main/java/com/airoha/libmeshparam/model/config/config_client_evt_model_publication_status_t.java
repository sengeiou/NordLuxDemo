package com.airoha.libmeshparam.model.config;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_MODEL_PUBLICATION_STATUS.
 */

public class config_client_evt_model_publication_status_t extends ble_mesh_base_status_t implements Serializable{
    /** Status Code for the requesting message. */
    public byte status;
    /** The unicast address of the element, all other address types are prohibited. */
    public short element_address;
    /** The new Publish Address state for the model. */
    public short publish_address;
    /** The index of application key used for this publication. */
    public short appkey_index;
    /** The value of the friendship credential flag. */
    public short credential_flag;
    /** Reserved for future use. */
    public short rfu;
    /** The default TTL value for the outgoing messages. */
    public byte publish_ttl;
    /** The period for periodic status publishing. */
    public byte publish_period;
    /** The number of retransmissions for each published message. */
    public byte publish_retransmit_count;
    /** The number of 50-millisecond steps between retransmissions. */
    public byte publish_retransmit_interval_steps;
    /** SIG Model ID or Vendor Model ID. */
    public int model_id;
}
