package com.airoha.libmesh.listener;

import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_attention_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_current_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_fault_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_period_status_t;

public interface MeshHealthModelListener {

    void onMeshHealthCurrentStatusReceived(ble_mesh_health_client_evt_current_status_t healthCurrentStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshHealthModel#healthClientClearFault(short, byte, short, boolean)}
     * , {@link com.airoha.btdlib.core.MeshHealthModel#healthClientGetFault(short, byte, short)}
     * and {@link com.airoha.btdlib.core.MeshHealthModel#healthClientTestFault(short, byte, byte, short, boolean)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param healthFaultStatus  The result of command execution.
     */
    void onMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t healthFaultStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshHealthModel#healthClientGetAttention(short, byte)}
     * and {@link com.airoha.btdlib.core.MeshHealthModel#healthClientSetAttention(short, byte, byte, boolean)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param healthAttentionStatus  The result of command execution.
     */
    void onMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t healthAttentionStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshHealthModel#healthClientGetPeriod(short, byte)}
     * and {@link com.airoha.btdlib.core.MeshHealthModel#healthClientSetPeriod(short, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param healthPeriodStatus  The result of command execution.
     */
    void onMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t healthPeriodStatus);
}
