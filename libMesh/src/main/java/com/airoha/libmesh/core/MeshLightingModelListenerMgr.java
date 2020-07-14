package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshLightingModelListener;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_temperature_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_temperature_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_hue_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_saturation_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_last_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_linear_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_status_t;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class MeshLightingModelListenerMgr {
    private ConcurrentHashMap<String, MeshLightingModelListener> mListenerMap;

    public MeshLightingModelListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshLightingModelListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t lightnessStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightnessStatusReceived(lightnessStatus);
        }
    }

    public synchronized void onMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t linearLightnessStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLinearLightnessStatusReceived(linearLightnessStatus);
        }
    }

    public synchronized void onMeshLastLightnessStatusReceived(ble_mesh_lightness_client_evt_last_status_t lastLightnessStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLastLightnessStatusReceived(lastLightnessStatus);
        }
    }

    public synchronized void onMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t defaultLightnessStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshDefaultLightnessStatusReceived(defaultLightnessStatus);
        }
    }

    public synchronized void onMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t rangeLightnessStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshRangeLightnessStatusReceived(rangeLightnessStatus);
        }
    }

    public synchronized void onMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t ctlStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightCtlStatusReceived(ctlStatus);
        }
    }

    public synchronized void onMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t ctlTemperatureStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightCtlTemperatureStatusReceived(ctlTemperatureStatus);
        }
    }

    public synchronized void onMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t ctlTemperatureRangeStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightCtlTemperatureRangeStatusReceived(ctlTemperatureRangeStatus);
        }
    }

    public synchronized void onMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t ctlDefaultStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightCtlDefaultStatusReceived(ctlDefaultStatus);
        }
    }

    public synchronized void onMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t hslStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightHslStatusReceived(hslStatus);
        }
    }
    public synchronized void onMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t defaultHslStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightDefaultHslStatusReceived(defaultHslStatus);
        }
    }

    public synchronized void onMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t hslRangeStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightHslRangeStatusReceived(hslRangeStatus);
        }
    }

    public synchronized void onMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t hueStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightHueStatusReceived(hueStatus);
        }
    }

    public synchronized void onMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t saturationStatus) {
        for (MeshLightingModelListener listener:mListenerMap.values()) {
            listener.onMeshLightSaturationStatusReceived(saturationStatus);
        }
    }

}
