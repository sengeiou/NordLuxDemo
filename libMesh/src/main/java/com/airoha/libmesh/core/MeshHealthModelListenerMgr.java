package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshHealthModelListener;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_attention_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_current_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_fault_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_period_status_t;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class MeshHealthModelListenerMgr {
    private ConcurrentHashMap<String, MeshHealthModelListener> mListenerMap;

    public MeshHealthModelListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshHealthModelListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshHealthCurrentStatusReceived(ble_mesh_health_client_evt_current_status_t healthCurrentStatus) {
        for (MeshHealthModelListener listener:mListenerMap.values()) {
            listener.onMeshHealthCurrentStatusReceived(healthCurrentStatus);
        }
    }

    public synchronized void onMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t healthFaultStatus) {
        for (MeshHealthModelListener listener:mListenerMap.values()) {
            listener.onMeshHealthFaultStatusReceived(healthFaultStatus);
        }
    }

    public synchronized void onMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t healthAttentionStatus) {
        for (MeshHealthModelListener listener:mListenerMap.values()) {
            listener.onMeshHealthAttentionStatusReceived(healthAttentionStatus);
        }
    }

    public synchronized void onMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t healthPeriodStatus) {
        for (MeshHealthModelListener listener:mListenerMap.values()) {
            listener.onMeshHealthPeriodStatusReceived(healthPeriodStatus);
        }
    }
}
