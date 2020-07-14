package com.airoha.libmesh.core;

import android.bluetooth.BluetoothDevice;

import com.airoha.libmesh.listener.MeshProvisionListener;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class MeshProvisionListenerMgr {
    private ConcurrentHashMap<String, MeshProvisionListener> mListenerMap;

    public MeshProvisionListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshProvisionListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {
        for (MeshProvisionListener listener:mListenerMap.values()) {
            listener.onMeshUdFound(device, rssi, uuid, oobInfo, uriHash);
        }
    }

    public synchronized void onMeshProvCapReceived(ble_mesh_prov_capabilities_t provCap) {
        for (MeshProvisionListener listener:mListenerMap.values()) {
            listener.onMeshProvCapReceived(provCap);
        }
    }

    public synchronized void onMeshProvStateChanged(boolean state, byte[] deviceKey, short address) {
        for (MeshProvisionListener listener:mListenerMap.values()) {
            listener.onMeshProvStateChanged(state, deviceKey, address);
        }
    }

    public synchronized void onMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response provAliResp) {
        for (MeshProvisionListener listener:mListenerMap.values()) {
            listener.onMeshAliProvisioningResponse(provAliResp);
        }
    }

    public synchronized void onMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice) {
        for (MeshProvisionListener listener:mListenerMap.values()) {
            listener.onMeshAliProvisioningConfirmationDevice(provAliConfirmDevice);
        }
    }
}
