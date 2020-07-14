package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshServiceListener;
import java.util.concurrent.ConcurrentHashMap;

class MeshServiceListenerMgr {
    private ConcurrentHashMap<String, MeshServiceListener> mListenerMap;

    public MeshServiceListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshServiceListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshProvisionServiceFound() {
        for (MeshServiceListener listener:mListenerMap.values()) {
            listener.onMeshProvisionServiceFound();
        }
    }

    public synchronized void onMeshProxyServiceFound() {
        for (MeshServiceListener listener:mListenerMap.values()) {
            listener.onMeshProxyServiceFound();
        }
    }
}
