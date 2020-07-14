package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshGenericOnOffModelListener;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_battery_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_default_transition_time_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_global_location_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_level_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_local_location_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_on_power_up_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_onoff_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_default_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_last_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_level_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_range_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_properties_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_property_status_t;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class MeshGenericOnOffModelListenerMgr {
    private ConcurrentHashMap<String, MeshGenericOnOffModelListener> mListenerMap;

    public MeshGenericOnOffModelListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshGenericOnOffModelListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t genericOnOffStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericOnOffStatusReceived(genericOnOffStatus);
        }
    }

    public synchronized void onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t genericLevelStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericLevelStatusReceived(genericLevelStatus);
        }
    }

    public synchronized void onMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t defaultTransitionTimeStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericDefaultTransitionTimeStatusReceived(defaultTransitionTimeStatus);
        }
    }

    public synchronized void onMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t onPowerUpStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericOnPowerUpStatusReceived(onPowerUpStatus);
        }
    }

    public synchronized void onMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t powerLevelStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericPowerLevelStatusReceived(powerLevelStatus);
        }
    }

    public synchronized void onMeshGenericPowerLastStatusReceived(ble_mesh_generic_client_evt_power_last_status_t powerLastStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericPowerLastStatusReceived(powerLastStatus);
        }
    }

    public synchronized void onMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t powerDefaultStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericPowerDefaultStatusReceived(powerDefaultStatus);
        }
    }

    public synchronized void onMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t powerRangeStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericPowerRangeStatusReceived(powerRangeStatus);
        }
    }

    public synchronized void onMeshGenericBatteryStatusReceived(ble_mesh_generic_client_evt_battery_status_t batteryStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericBatteryStatusReceived(batteryStatus);
        }
    }

    public synchronized void onMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t locationGlobalStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericLocationGlobalStatusReceived(locationGlobalStatus);
        }
    }

    public synchronized void onMeshGenericLocationLocalStatusReceived(ble_mesh_generic_client_evt_local_location_status_t locationLocalStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericLocationLocalStatusReceived(locationLocalStatus);
        }
    }

    public synchronized void onMeshGenericManufacturerPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t manufacturerPropertiesStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericManufacturerPropertiesStatusReceived(manufacturerPropertiesStatus);
        }
    }

    public synchronized void onMeshGenericManufacturerPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t manufacturerPropertyStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericManufacturerPropertyStatusReceived(manufacturerPropertyStatus);
        }
    }

    public synchronized void onMeshGenericAdminPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t adminPropertiesStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericAdminPropertiesStatusReceived(adminPropertiesStatus);
        }
    }

    public synchronized void onMeshGenericAdminPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t adminPropertyStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericAdminPropertyStatusReceived(adminPropertyStatus);
        }
    }

    public synchronized void onMeshGenericUserPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t userPropertiesStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericUserPropertiesStatusReceived(userPropertiesStatus);
        }
    }

    public synchronized void onMeshGenericUserPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t userPropertyStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericUserPropertyStatusReceived(userPropertyStatus);
        }
    }

    public synchronized void onMeshGenericClientPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t clientPropertiesStatus) {
        for (MeshGenericOnOffModelListener listener:mListenerMap.values()) {
            listener.onMeshGenericClientPropertiesStatusReceived(clientPropertiesStatus);
        }
    }
}
