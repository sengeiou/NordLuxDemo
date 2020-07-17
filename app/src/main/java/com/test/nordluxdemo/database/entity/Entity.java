package com.test.nordluxdemo.database.entity;

import com.test.nordluxdemo.ble.mesh.MeshUtils;

import java.util.ArrayList;

public class Entity {
    private ArrayList<MeshUtils.PD_INFO> mMeshDevices;
   int lightCount=9;
   int lightState;

    public int getLightCount() {
        return lightCount;
    }

    public void setLightCount(int lightCount) {
        this.lightCount = lightCount;
    }

    public int getLightState() {
        return lightState;
    }

    public void setLightState(int lightState) {
        this.lightState = lightState;
    }
}
