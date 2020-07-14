package com.airoha.libmeshparam.model.config;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_MODEL_SUBSCRIPTION_STATUS.
 */


public class config_client_evt_model_subscription_status_t extends ble_mesh_base_status_t implements Serializable{
    /** Status Code for the requesting message. */
    public byte status;
    /** The address of the element. */
    public short element_address;
    /** The address that was used to modify the Subscription List or the unassigned address. */
    public short suscribed_address;
    /** SIG Model ID or Vendor Model ID. */
    public int model_id;         
}
