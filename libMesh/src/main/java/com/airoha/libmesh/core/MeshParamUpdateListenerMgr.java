package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshParamUpdateListener;

import java.util.concurrent.ConcurrentHashMap;


class MeshParamUpdateListenerMgr {
    private ConcurrentHashMap<String, MeshParamUpdateListener> mListenerMap;

    public MeshParamUpdateListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshParamUpdateListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshIvUpdated(int iv_index, byte state) {
        for (MeshParamUpdateListener listener:mListenerMap.values()) {
            listener.onMeshIvUpdated(iv_index, state);
        }
    }
}

