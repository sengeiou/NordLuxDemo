package com.airoha.libmeshparam.model.config;
import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**
 * Event parameter of configuration client model event @ref CONFIG_CLIENT_EVT_MODEL_APP_STATUS.
 */


public class config_client_evt_model_app_status_t extends ble_mesh_base_status_t implements Serializable{
    /** Status code for the requesting message. */
    public byte status;
    /** Address of the element. */
    public short element_address;
    /** Index of the AppKey. */
    public short appkey_index;
    /** SIG Model ID or Vendor Model ID. */
    public int model_id;         
}
