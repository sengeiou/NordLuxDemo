package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshParamUpdateListener;
import com.airoha.libnativemesh.AirohaMesh;

import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

public class MeshParam {
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshParamUpdateListenerMgr mMeshParamUpdateListenerMgr = null;

    MeshParam(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshParamUpdateListenerMgr = new MeshParamUpdateListenerMgr();
    }

    public void addListener(String name, MeshParamUpdateListener listener) {
        mMeshParamUpdateListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshParamUpdateListenerMgr.removeListener(name);
    }
}
