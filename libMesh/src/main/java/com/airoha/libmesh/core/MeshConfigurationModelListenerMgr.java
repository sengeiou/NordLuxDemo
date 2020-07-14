package com.airoha.libmesh.core;

import com.airoha.libmesh.listener.MeshConfigurationModelListener;
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

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class MeshConfigurationModelListenerMgr {
    private ConcurrentHashMap<String, MeshConfigurationModelListener> mListenerMap;

    public MeshConfigurationModelListenerMgr(){
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, MeshConfigurationModelListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigCompositionReceived(configComposition);
        }
    }

    public synchronized void onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t configNetkeyStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigNetKeyStatusReceived(configNetkeyStatus);
        }
    }

    public synchronized void onMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t configNetkeyList) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigNetKeyListReceived(configNetkeyList);
        }
    }

    public synchronized void onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus){
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigAppKeyStatusReceived(configAppkeyStatus);
        }
    }

    public synchronized void onMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t configAppkeyList) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigAppKeyListReceived(configAppkeyList);
        }
    }

    public synchronized void onMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t configModelAppStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigModelAppStatusReceived(configModelAppStatus);
        }
    }

    public synchronized void onMeshConfigModelAppListReceived(config_client_evt_model_app_list_t configModelAppList) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigModelAppListReceived(configModelAppList);
        }
    }

    public synchronized void onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t subscriptionStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigSubscriptionStatusReceived(subscriptionStatus);
        }
    }

    public synchronized void onMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t publicationStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigPublicationStatusReceived(publicationStatus);
        }
    }

    public synchronized void onMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t beaconStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigBeaconStatusReceived(beaconStatus);
        }
    }

    public synchronized void onMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t defaultTtlStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigDefaultTtlStatusReceived(defaultTtlStatus);
        }
    }

    public synchronized void onMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t gattPorxyStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigGattPorxyStatusReceived(gattPorxyStatus);
        }
    }

    public synchronized void onMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigKeyRefreshPhaseStatusReceived(keyRefreshPhaseStatus);
        }
    }

    public synchronized void onMeshConfigFriendStatusReceived(config_client_evt_friend_status_t friendStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigFriendStatusReceived(friendStatus);
        }
    }

    public synchronized void onMeshConfigRelayStatusReceived(config_client_evt_relay_status_t relayStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigRelayStatusReceived(relayStatus);
        }
    }

    public synchronized void onMeshConfigModelSubscriptionListReceived(config_client_evt_model_subscription_list_t modelSubscriptionList) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigModelSubscriptionListReceived(modelSubscriptionList);
        }
    }

    public synchronized void onMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t nodeIdentityStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigNodeIdentityStatusReceived(nodeIdentityStatus);
        }
    }

    public synchronized void onMeshConfigResetNodeStatusReceived(int status) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigResetNodeStatusReceived(status);
        }
    }

    public synchronized void onMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigHeartbeatPublicationStatusReceived(heartbeatPublicationStatus);
        }
    }

    public synchronized void onMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigHeartbeatSubscriptionStatusReceived(heartbeatSubscriptionStatus);
        }
    }

    public synchronized void onMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t networkTransmitStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigNetworkTransmitStatusReceived(networkTransmitStatus);
        }
    }

    public synchronized void onMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus) {
        for (MeshConfigurationModelListener listener:mListenerMap.values()) {
            listener.onMeshConfigLpnPollTimeoutStatusReceived(lpnPollTimeoutStatus);
        }
    }
}
