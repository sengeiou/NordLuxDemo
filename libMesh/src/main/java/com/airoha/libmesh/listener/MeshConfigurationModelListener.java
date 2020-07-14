package com.airoha.libmesh.listener;

import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.config.config_client_evt_appkey_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_appkey_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_beacon_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_composition_data_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_default_ttl_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_friend_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_gatt_proxy_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_heartbeat_publication_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_heartbeat_subscription_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_key_refresh_phase_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_lpn_poll_timeout_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_app_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_app_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_publication_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_subscription_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_subscription_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_netkey_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_netkey_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_network_transmit_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_node_identity_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_relay_status_t;

public interface MeshConfigurationModelListener {

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getComposition(short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configComposition  The composition data.
     */
    void onMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#addConfigNetKey(short, short, byte[])}
     * , {@link com.airoha.libmesh.core.MeshConfigurationModel#updateConfigNetKey(short, short, byte[])}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#deleteConfigNetKey(short, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configNetkeyStatus   The result of command execution.
     */
    void onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t configNetkeyStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getConfigNetKey(short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configNetkeyList  The netkey list.
     */
    void onMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t configNetkeyList);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#addConfigAppKey(short, short, short, byte[])}. <p>
     * , {@link com.airoha.libmesh.core.MeshConfigurationModel#updateConfigAppKey(short, short, short, byte[])}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#deleteConfigAppKey(short, short, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configAppkeyStatus  The result of command execution.
     */
    void onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getConfigAppKey(short, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configAppkeyList  The composition data.
     */
    void onMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t configAppkeyList);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#bindConfigModelApp(short, short, short, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configModelAppStatus  The result of command execution.
     */
    void onMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t configModelAppStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getSigModelAppList(short, short, int)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#getVendorModelAppList(short, short, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param configModelAppList  The model app list.
     */
    void onMeshConfigModelAppListReceived(config_client_evt_model_app_list_t configModelAppList);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#addModelSubscription(short, short, short, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param subscriptionStatus  The result of command execution.
     */
    void onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t subscriptionStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#setModelPublication(short, short, short, short, boolean, byte, byte, byte, byte, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param publicationStatus  The result of command execution.
     */
    void onMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t publicationStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getBeacon(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setBeacon(ENUM_DEF.ble_mesh_feature_state_t, short, byte, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param beaconStatus  The result of command execution.
     */
    void onMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t beaconStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getDefaultTTL(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setDefaultTTL(short, byte, byte, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param defaultTtlStatus  The result of command execution.
     */
    void onMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t defaultTtlStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getGattProxy(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setGattProxy(ENUM_DEF.ble_mesh_feature_state_t, short, byte, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param gattPorxyStatus  The result of command execution.
     */
    void onMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t gattPorxyStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getKeyRefreshPhase(short, short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setKeyRefreshPhase(short, short, byte, byte, short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param keyRefreshPhaseStatus  The result of command execution.
     */
    void onMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getFriend(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setFriend(ENUM_DEF.ble_mesh_feature_state_t, short, byte, short)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param friendStatus  The result of command execution.
     */
    void onMeshConfigFriendStatusReceived(config_client_evt_friend_status_t friendStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getRelay(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setRelay(ENUM_DEF.ble_mesh_feature_state_t, byte, byte, short, byte, short)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param relayStatus  The result of command execution.
     */
    void onMeshConfigRelayStatusReceived(config_client_evt_relay_status_t relayStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getSigModelSubscription(short, short, int)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#getVendorModelSubscription(short, short, int)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param modelSubscriptionList  The result of command execution.
     */
    void onMeshConfigModelSubscriptionListReceived(config_client_evt_model_subscription_list_t modelSubscriptionList);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getNodeIdentity(short, short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setNodeIdentity(short, ENUM_DEF.ble_mesh_feature_state_t, short, byte, short)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param nodeIdentityStatus  The result of command execution.
     */
    void onMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t nodeIdentityStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getHeartbeatPublication(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setHeartbeatPublication(short, int, byte, byte, short, short, short, byte, short)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param heartbeatPublicationStatus  The result of command execution.
     */
    void onMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getHeartbeatSubscription(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setHeartbeatSubscription(short, short, byte, short, byte, short)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param heartbeatSubscriptionStatus  The result of command execution.
     */
    void onMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getNetworkTransmit(short, byte, short)}
     * and {@link com.airoha.libmesh.core.MeshConfigurationModel#setNetworkTransmit(byte, byte, short, byte, short)}  . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param networkTransmitStatus  The result of command execution.
     */
    void onMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t networkTransmitStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#getLpnPollTimeout(short, short, byte, short)} . <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param lpnPollTimeoutStatus  The result of command execution.
     */
    void onMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus);

    /**
     * Callback for {@link com.airoha.libmesh.core.MeshConfigurationModel#resetNode(short)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param status  The result of command execution.
     */
    void onMeshConfigResetNodeStatusReceived(int status);

}
