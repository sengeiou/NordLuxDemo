package com.airoha.libmesh.listener;

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

public interface MeshLightingModelListener {

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightLightnessModel#getLightness(short, byte)} and  {@link com.airoha.btdlib.core.MeshLightLightnessModel#setLightness(short, byte, short, byte, byte, byte, boolean)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param lightnessStatus  The result of command execution.
     */
    void onMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t lightnessStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightLightnessModel#getLinearLightness(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightLightnessModel#setLinearLightness(short, byte, short, byte, byte, byte, boolean)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param linearLightnessStatus  The result of command execution.
     */
    void onMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t linearLightnessStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightLightnessModel#getLastLightness(short, byte)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param lastLightnessStatus  The result of command execution.
     */
    void onMeshLastLightnessStatusReceived(ble_mesh_lightness_client_evt_last_status_t lastLightnessStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightLightnessModel#getDefaultLightness(short, byte)}
     * and {@link com.airoha.btdlib.core.MeshLightLightnessModel#setDefaultLightness(short, byte, short, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param defaultLightnessStatus  The result of command execution.
     */
    void onMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t defaultLightnessStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightLightnessModel#getLightnessRange(short, byte)}
     * and {@link com.airoha.btdlib.core.MeshLightLightnessModel#setLightnessRange(short, byte, short, short, boolean)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param rangeLightnessStatus  The result of command execution.
     */
    void onMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t rangeLightnessStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightCTLModel#getLightCTL(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightCTLModel#setLightCTL(short, byte, short, short, short, byte, byte, byte, boolean)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param ctlStatus  The result of command execution.
     */
    void onMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t ctlStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightCTLModel#getLightCtlTemperature(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightCTLModel#setLightCtlTemperature(short, byte, short, short, byte, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param ctlTemperatureStatus  The result of command execution.
     */
    void onMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t ctlTemperatureStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightCTLModel#getLightCtlTemperatureRange(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightCTLModel#setLightCtlTemperatureRange(short, byte, short, short, boolean)}   . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param ctlTemperatureRangeStatus  The result of command execution.
     */
    void onMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t ctlTemperatureRangeStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightCTLModel#getLightDefaultCtl(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightCTLModel#setLightDefaultCtl(short, byte, short, short, short, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param ctlDefaultStatus  The result of command execution.
     */
    void onMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t ctlDefaultStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightHSLModel#getLightHSL(short, byte)} and  {@link com.airoha.btdlib.core.MeshLightHSLModel#setLightHSL(short, byte, short, short, short, byte, byte, byte, boolean)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param hslStatus  The result of command execution.
     */
    void onMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t hslStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightHSLModel#getLightDefaultHsl(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightHSLModel#setLightDefaultHsl(short, byte, short, short, short, boolean)}   . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param defaultHslStatus  The result of command execution.
     */
    void onMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t defaultHslStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightHSLModel#getLightHslRange(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightHSLModel#setLightHslRange(short, byte, short, short, short, short, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param hslRangeStatus  The result of command execution.
     */
    void onMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t hslRangeStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightHSLModel#getLightHue(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightHSLModel#setLightHue(short, byte, short, byte, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param hueStatus  The result of command execution.
     */
    void onMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t hueStatus);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshLightHSLModel#getLightSaturation(short, byte)}
     * and  {@link com.airoha.btdlib.core.MeshLightHSLModel#setLightSaturation(short, byte, short, byte, byte, byte, boolean)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param saturationStatus  The result of command execution.
     */
    void onMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t saturationStatus);

}
