package com.airoha.libmeshparam.model.health;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of health client model event @ref BLE_MESH_HEALTH_CLIENT_EVT_ATTENTION_STATUS. */

public class ble_mesh_health_client_evt_attention_status_t extends ble_mesh_base_status_t implements Serializable {
    /**< Value of the Attention Timer state, which represents the remaining duration of the attention state of a server in seconds. */
    public byte attention;
}
