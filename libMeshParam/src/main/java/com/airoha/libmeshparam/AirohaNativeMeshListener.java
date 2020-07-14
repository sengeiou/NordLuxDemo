package com.airoha.libmeshparam;

import com.airoha.libmeshparam.*;
import com.airoha.libmeshparam.prov.*;
import com.airoha.libmeshparam.model.*;
import com.airoha.libmeshparam.model.config.*;
import com.airoha.libmeshparam.model.generic.*;
import com.airoha.libmeshparam.model.health.*;
import com.airoha.libmeshparam.model.lighting.*;

public interface AirohaNativeMeshListener {
    void OnMeshInitDone();

    void OnMeshGattDataOut(byte[] gattDataOut);

    void OnMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response provAliResp);

    void OnMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice);

    void OnMeshProvCapabilitiesUpdated(ble_mesh_prov_capabilities_t provCap);

    void OnMeshProvRequestOobAuthValue(ble_mesh_prov_request_oob_auth_value oobAuthValue);

    void OnMeshProvStateChanged(ble_mesh_evt_prov_done_t provDone);

    void OnMeshIvUpdated(ble_mesh_evt_iv_update_t ivIndexUpdate);

    void OnMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition);

    void OnMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t configNetkeyStatus);

    void OnMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t configNetkeyList);

    void OnMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus);

    void OnMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t configAppkeyList);

    void OnMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t configModelAppStatus);

    void OnMeshConfigModelAppListReceived(config_client_evt_model_app_list_t configModelAppList);

    void OnMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t subscriptionStatus);

    void OnMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t publicationStatus);

    void OnMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t beaconStatus);

    void OnMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t defaultTtlStatus);

    void OnMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t gattPorxyStatus);

    void OnMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus);

    void OnMeshConfigFriendStatusReceived(config_client_evt_friend_status_t friendStatus);

    void OnMeshConfigRelayStatusReceived(config_client_evt_relay_status_t relayStatus);

    void OnMeshConfigModelSubscriptionListReceived(config_client_evt_model_subscription_list_t modelSubscriptionList);

    void OnMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t nodeIdentityStatus);

    void OnMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus);

    void OnMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus);

    void OnMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t networkTransmitStatus);

    void OnMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus);

    void OnMeshConfigResetNodeStatusReceived(int status);

    void OnMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t genericOnOffStatus);

    void OnMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t genericLevelStatus);

    void OnMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t defaultTransitionTimeStatus);

    void OnMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t onPowerUpStatus);

    void OnMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t powerLevelStatus);

    void OnMeshGenericPowerLastStatusReceived(ble_mesh_generic_client_evt_power_last_status_t powerLastStatus);

    void OnMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t powerDefaultStatus);

    void OnMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t powerRangeStatus);

    void OnMeshGenericBatteryStatusReceived(ble_mesh_generic_client_evt_battery_status_t batteryStatus);

    void OnMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t locationGlobalStatus);

    void OnMeshGenericLocationLocalStatusReceived(ble_mesh_generic_client_evt_local_location_status_t locationLocalStatus);

    void OnMeshGenericManufacturerPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t manufacturerPropertiesStatus);

    void OnMeshGenericManufacturerPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t manufacturerPropertyStatus);

    void OnMeshGenericAdminPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t adminPropertiesStatus);

    void OnMeshGenericAdminPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t adminPropertyStatus);

    void OnMeshGenericUserPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t userPropertiesStatus);

    void OnMeshGenericUserPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t userPropertyStatus);

    void OnMeshGenericClientPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t clientPropertiesStatus);

    void OnMeshHealthCurrentStatusReceived(ble_mesh_health_client_evt_current_status_t healthCurrentStatus);

    void OnMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t healthFaultStatus);

    void OnMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t healthPeriodStatus);

    void OnMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t healthAttentionStatus);

    void OnMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t lightnessStatus);

    void OnMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t linearLightnessStatus);

    void OnMeshLastLightnessStatusReceived(ble_mesh_lightness_client_evt_last_status_t lastLightnessStatus);

    void OnMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t defaultLightnessStatus);

    void OnMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t rangeLightnessStatus);

    void OnMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t ctlStatus);

    void OnMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t ctlTemperatureStatus);

    void OnMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t ctlTemperatureRangeStatus);

    void OnMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t ctlDefaultStatus);

    void OnMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t hslStatus);

    void OnMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t defaultHslStatus);

    void OnMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t hslRangeStatus);

    void OnMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t hueStatus);

    void OnMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t saturationStatus);

    void OnMeshVendorModelMsgReceived(ble_mesh_access_message_rx_t msg);
}
