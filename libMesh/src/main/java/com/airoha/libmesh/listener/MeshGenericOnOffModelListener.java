package com.airoha.libmesh.listener;

import com.airoha.libmeshparam.ENUM_DEF;
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

public interface MeshGenericOnOffModelListener {

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getGenericOnOff(short, byte)} and  {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setGenericOnOff(short, byte, byte, byte, byte, byte, boolean)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param genericOnOffStatus  The result of command execution.
     */
    void onMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t genericOnOffStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getGenericLevel(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setGenericLevel(short, byte, short, byte, byte, byte, boolean)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setGenericLevelDelta(short, byte, int, byte, byte, byte, boolean)}
     * and {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setGenericLevelMove(short, byte, int, byte, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param genericLevelStatus  The result of command execution.
     */
    void onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t genericLevelStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getDefaultTransitionTime(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setDefaultTransitionTime(short, byte, byte, boolean)}   . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param defaultTransitionTimeStatus  The result of command execution.
     */
    void onMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t defaultTransitionTimeStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getOnPowerUp(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setOnPowerUp(short, byte, ENUM_DEF.ble_mesh_model_generic_on_powerup_t, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param onPowerUpStatus  The result of command execution.
     */
    void onMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t onPowerUpStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getPowerLevelActual(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setPowerLevelActual(short, byte, short, byte, byte, byte, boolean)}   . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param powerLevelStatus  The result of command execution.
     */
    void onMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t powerLevelStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getPowerLevelLast(short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param powerLastStatus  The result of command execution.
     */
    void onMeshGenericPowerLastStatusReceived(ble_mesh_generic_client_evt_power_last_status_t powerLastStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getPowerLevelDefault(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setPowerLevelDefault(short, byte, short, boolean)}   . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param powerDefaultStatus  The result of command execution.
     */
    void onMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t powerDefaultStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getPowerLevelRange(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setPowerLevelRange(short, byte, short, short, boolean)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param powerRangeStatus  The result of command execution.
     */
    void onMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t powerRangeStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getBattery(short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param batteryStatus  The result of command execution.
     */
    void onMeshGenericBatteryStatusReceived(ble_mesh_generic_client_evt_battery_status_t batteryStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getLocationGlobal(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setLocationGlobal(short, byte, int, int, int, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param locationGlobalStatus  The result of command execution.
     */
    void onMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t locationGlobalStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getLocationLocal(short, byte)}
     * , {@link com.airoha.btdlib.core.MeshGenericOnOffModel#setLocationLocal(short, byte, short, short, short, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param locationLocalStatus  The result of command execution.
     */
    void onMeshGenericLocationLocalStatusReceived(ble_mesh_generic_client_evt_local_location_status_t locationLocalStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperties(ENUM_DEF.ble_mesh_device_property_type_t, short, byte)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param manufacturerPropertiesStatus  The result of command execution.
     */
    void onMeshGenericManufacturerPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t manufacturerPropertiesStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperty(ENUM_DEF.ble_mesh_device_property_type_t, short, short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param manufacturerPropertyStatus  The result of command execution.
     */
    void onMeshGenericManufacturerPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t manufacturerPropertyStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperties(ENUM_DEF.ble_mesh_device_property_type_t, short, byte)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param adminPropertiesStatus  The result of command execution.
     */
    void onMeshGenericAdminPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t adminPropertiesStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperty(ENUM_DEF.ble_mesh_device_property_type_t, short, short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param adminPropertyStatus  The result of command execution.
     */
    void onMeshGenericAdminPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t adminPropertyStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperties(ENUM_DEF.ble_mesh_device_property_type_t, short, byte)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param userPropertiesStatus  The result of command execution.
     */
    void onMeshGenericUserPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t userPropertiesStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getProperty(ENUM_DEF.ble_mesh_device_property_type_t, short, short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param userPropertyStatus  The result of command execution.
     */
    void onMeshGenericUserPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t userPropertyStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshGenericOnOffModel#getClientProperties(short, short, byte)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param clientPropertiesStatus  The result of command execution.
     */
    void onMeshGenericClientPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t clientPropertiesStatus);
}
