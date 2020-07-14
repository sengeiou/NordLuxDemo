package com.airoha.libmesh.core;


import com.airoha.libmesh.listener.MeshVendorModelListener;
import com.airoha.libmeshparam.model.ble_mesh_access_message_rx_t;

import java.util.concurrent.ConcurrentHashMap;

public class MeshVendorModelListenerMgr {
    private ConcurrentHashMap<String, MeshVendorModelListener> mListenerMap;

    public MeshVendorModelListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshVendorModelListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }
    public synchronized void OnMeshVendorModelMsgReceived(ble_mesh_access_message_rx_t msg){
        for (MeshVendorModelListener listener:mListenerMap.values()) {
            listener.OnMeshVendorModelMsgReceived(msg);
        }
    }
}
