package com.airoha.libmeshparam.model.health;

import com.airoha.libmeshparam.model.ble_mesh_base_status_t;

import java.io.Serializable;

/**  Event parameter of health client model event @ref BLE_MESH_HEALTH_CLIENT_EVT_PERIOD_STATUS. */

public class ble_mesh_health_client_evt_period_status_t extends ble_mesh_base_status_t implements Serializable{
    /**< Divider for the Publish Period. Modified Publish Period is used for sending Current Health Status messages when there are active faults to communicate.*/
    public byte fast_period_divisor;
}
