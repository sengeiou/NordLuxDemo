package com.airoha.libmesh.core;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import com.airoha.btdlib.core.AirohaLink;
import com.airoha.btdlib.core.GattStateListener;
import com.airoha.libmesh.listener.MeshServiceListener;

import com.airoha.libmeshparam.model.ble_mesh_access_message_rx_t;
import com.airoha.libmeshparam.prov.ble_mesh_evt_iv_update_t;
import com.airoha.libmeshparam.prov.ble_mesh_prov_request_oob_auth_value;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.AirohaNativeMeshListener;

import com.airoha.libmeshparam.AirohaMeshUUID;
import com.airoha.libmeshparam.PROV_INPUT_DATA;
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
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_attention_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_current_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_fault_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_period_status_t;
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
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_done_t;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;

import com.airoha.libmesh.util.ByteHelper;
import com.airoha.libnativemesh.MeshUtils;

import java.util.List;

public class AirohaMeshMgr implements AirohaNativeMeshListener, GattStateListener {
    static final String TAG = "AirohaMeshMgr";
    static final String NOT_INIT_ERROR = "Not init yet";
    static final int mMTU = 115;

    @Override
    public void onGattConnected(BluetoothGatt gatt) {

    }

    @Override
    public void onGattDisconnected(BluetoothGatt gatt) {
        getMeshCore().updateServiceState(false);
    }

    @Override
    public void onRequestMtuChangeStatus(boolean isAccepted) {

    }

    @Override
    public void onNewMtu(int mtu) {

    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {

            if(Build.VERSION.SDK_INT >= 21){
                gatt.requestConnectionPriority(BluetoothGatt.CONNECTION_PRIORITY_HIGH);
            }

            new SearchAirohaMeshServiceThread(gatt.getServices()).start();
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        final byte[] data = characteristic.getValue();

        final String uuid = characteristic.getUuid().toString();
        Log.d(TAG, "notifi charc:" + uuid + "; get value: " + ByteHelper.bytesToHex(data));

        if (uuid.equalsIgnoreCase(AirohaMeshUUID.MESH_PROVISION_SERVICE_DATA_OUT_UUID)
                || uuid.equalsIgnoreCase(AirohaMeshUUID.MESH_PROXY_SERVICE_DATA_OUT_UUID)) {
            getMeshCore().gatttDataIn(data);
        }
    }

    static class SWITCH_STATE {
        public static int SWITCH_SETTING_STATE_IDLE = 0;
        public static int SWITCH_SETTING_STATE_PROVISIONING = 1;
        public static int SWITCH_SETTING_STATE_PROVISIONED = 2;
        public static int SWITCH_SETTING_STATE_GET_COMPOSITION = 3;
        public static int SWITCH_SETTING_STATE_GET_COMPOSITION_DONE = 4;
        public static int SWITCH_SETTING_STATE_ADD_APPKEY = 5;
        public static int SWITCH_SETTING_STATE_ADD_APPKEY_DONE = 6;
        public static int SWITCH_SETTING_STATE_BIND_MODEL = 7;
        public static int SWITCH_SETTING_STATE_BIND_MODEL_DONE = 8;
        public static int SWITCH_SETTING_STATE_BUSY = 9;
        public static int SWITCH_SETTING_STATE_DONE = 10;
    }

    AirohaLink mAirohaLink = null;
    MeshServiceListenerMgr mMeshServiceListenerMgr = null;

    static int mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
    static boolean mIsMeshInitDone = false;
    PROV_INPUT_DATA mProvData;

    private BluetoothGattCharacteristic mMeshProvisionWriteCharc;
    private BluetoothGattCharacteristic mMeshProxyWriteCharc;

    private MeshCore mMeshCore;
    private MeshConfig mMeshConfig;
    private MeshProvision mMeshProvision;
    private MeshConfigurationModel mMeshConfigurationModel;
    private MeshGenericOnOffModel mMeshGenericOnOffModel;
    private MeshHealthModel mMeshHealthModel;
    private MeshLightLightnessModel mMeshLightLightnessModel;
    private MeshLightCTLModel mMeshLightCTLModel;
    private MeshLightHSLModel mMeshLightHSLModel;
    private MeshVendorModel mMeshVendorModel;
    private MeshParam mMeshParam;

    public AirohaMeshMgr(AirohaLink airohaLink) {
        mAirohaLink = airohaLink;
        mMeshServiceListenerMgr = new MeshServiceListenerMgr();
        mAirohaLink.addGattStateListener(TAG, this);
    }

    public void destroy() {
        getMeshCore().destroy();
        mAirohaLink.removeGattStateListener(TAG);
        mAirohaLink = null;
    }

    public void addListener(String name, MeshServiceListener listener) {
        mMeshServiceListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshServiceListenerMgr.removeListener(name);
    }

    public MeshCore getMeshCore(){
        if (mMeshCore == null) {
            mMeshCore = new MeshCore(this);
        }
        return mMeshCore;
    }

    public MeshConfig getMeshConfig(){
        if (mMeshConfig == null) {
            mMeshConfig = new MeshConfig(this);
        }
        return mMeshConfig;
    }

    public MeshProvision getMeshProvision(){
        if (mMeshProvision == null) {
            mMeshProvision = new MeshProvision(this);
        }
        return mMeshProvision;
    }

    public MeshConfigurationModel getMeshConfigurationModel(){
        if (mMeshConfigurationModel == null) {
            mMeshConfigurationModel = new MeshConfigurationModel(this);
        }
        return mMeshConfigurationModel;
    }
    public MeshGenericOnOffModel getMeshGenericOnOffModel(){
        if (mMeshGenericOnOffModel == null) {
            mMeshGenericOnOffModel = new MeshGenericOnOffModel(this);
        }
        return mMeshGenericOnOffModel;
    }
    public MeshHealthModel getMeshHealthModel(){
        if (mMeshHealthModel == null) {
            mMeshHealthModel = new MeshHealthModel(this);
        }
        return mMeshHealthModel;
    }
    public MeshLightLightnessModel getMeshLightLightnessModel(){
        if (mMeshLightLightnessModel == null) {
            mMeshLightLightnessModel = new MeshLightLightnessModel(this);
        }
        return mMeshLightLightnessModel;
    }
    public MeshLightCTLModel getMeshLightCTLModel(){
        if (mMeshLightCTLModel == null) {
            mMeshLightCTLModel = new MeshLightCTLModel(this);
        }
        return mMeshLightCTLModel;
    }
    public MeshLightHSLModel getMeshLightHSLModel(){
        if (mMeshLightHSLModel == null) {
            mMeshLightHSLModel = new MeshLightHSLModel(this);
        }
        return mMeshLightHSLModel;
    }

    public MeshVendorModel getMeshVendorModel(){
        if (mMeshVendorModel == null) {
            mMeshVendorModel = new MeshVendorModel(this);
        }
        return mMeshVendorModel;
    }

    public MeshParam getMeshParam(){
        if (mMeshParam == null) {
            mMeshParam = new MeshParam(this);
        }
        return mMeshParam;
    }

    @Override
    public void OnMeshInitDone() {
        mIsMeshInitDone = true;
    }

    @Override
    public void OnMeshGattDataOut(byte[] gattDataOut) {
        Log.d(TAG, "MESH GATT DataOut:" + ByteHelper.bytesToHex(gattDataOut));
        if (mSwitchState == SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONING) {
            mAirohaLink.addWriteCharacteristicTask(mMeshProvisionWriteCharc, gattDataOut);
        } else {
            mAirohaLink.addWriteCharacteristicTask(mMeshProxyWriteCharc, gattDataOut);
        }
    }

    @Override
    public void OnMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response provAliResp) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mMeshProvision.mMeshProvisionListenerMgr.onMeshAliProvisioningResponse(provAliResp);
        }
    }

    @Override
    public void OnMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mMeshProvision.mMeshProvisionListenerMgr.onMeshAliProvisioningConfirmationDevice(provAliConfirmDevice);
        }
    }

    static class ALGORITHM {
        public static byte BLE_MESH_PROV_START_ALGORITHM_FIPS_P256_ELLIPTIC_CURVE = 0x00;
    }

    static class PUBLIC_KEY {
        public static byte BLE_MESH_PROV_START_PUBLIC_KEY_NO_OOB = 0x00;
        public static byte BLE_MESH_PROV_START_PUBLIC_KEY_OOB = 0x01;
    }

    static class AUTH_METHOD {
        public static byte BT_MESH_PROV_START_AUTHEN_METHOD_NO_OOB = 0x00;
        public static byte BLE_MESH_PROV_START_AUTHEN_METHOD_STATIC_OOB = 0x01;
        public static byte BLE_MESH_PROV_START_AUTHEN_METHOD_OUTPUT_OOB = 0x02;
        public static byte BLE_MESH_PROV_START_AUTHEN_METHOD_INPUT_OOB = 0x03;
    }

    public static byte BT_MESH_PROV_CAPABILITY_OOB_STATIC_TYPE_SUPPORTED = 0x01;

    @Override
    public void OnMeshProvCapabilitiesUpdated(ble_mesh_prov_capabilities_t provCap) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mMeshProvision.mMeshProvisionListenerMgr.onMeshProvCapReceived(provCap);

            if (mProvData == null) {
                mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
            else {
                byte auth_method = AUTH_METHOD.BLE_MESH_PROV_START_AUTHEN_METHOD_STATIC_OOB;
                if(provCap.static_oob_type != BT_MESH_PROV_CAPABILITY_OOB_STATIC_TYPE_SUPPORTED){
                    auth_method = AUTH_METHOD.BT_MESH_PROV_START_AUTHEN_METHOD_NO_OOB;
                }

                int ret = AirohaMesh.getInstance().startProvision(
                        ALGORITHM.BLE_MESH_PROV_START_ALGORITHM_FIPS_P256_ELLIPTIC_CURVE,
                        PUBLIC_KEY.BLE_MESH_PROV_START_PUBLIC_KEY_NO_OOB,
                        auth_method,
                        (byte)0,
                        (byte)0,
                        mProvData.netkey, mProvData.netkey_index, mProvData.iv_index, mProvData.address, mProvData.flags);
                if (ret != 0) {
                    mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
                }
            }
        }
    }

    @Override
    public void OnMeshProvRequestOobAuthValue(ble_mesh_prov_request_oob_auth_value oobAuthValue) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());

            byte[] auth_value = new byte[] {
                    0x0a, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
                    0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08
            };
            AirohaMesh.getInstance().provideOobAuthValue(auth_value, (byte)16);
        }
    }

    @Override
    public void OnMeshProvStateChanged(ble_mesh_evt_prov_done_t provDone) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONED;
            mMeshProvision.mMeshProvisionListenerMgr.onMeshProvStateChanged(provDone.success, provDone.device_key, provDone.address);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshIvUpdated(ble_mesh_evt_iv_update_t ivIndexUpdate) {
        synchronized (this) {
            Log.d(TAG, new Object() {
            }.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONED;
            mMeshParam.mMeshParamUpdateListenerMgr.onMeshIvUpdated(ivIndexUpdate.iv_index, ivIndexUpdate.state);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_GET_COMPOSITION_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigCompositionReceived(configComposition);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t configNetkeyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_GET_COMPOSITION_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigNetKeyStatusReceived(configNetkeyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t configNetkeyList) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_GET_COMPOSITION_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigNetKeyListReceived(configNetkeyList);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigAppKeyStatusReceived(configAppkeyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t configAppkeyList) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigAppKeyListReceived(configAppkeyList);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t configModelAppStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigModelAppStatusReceived(configModelAppStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigModelAppListReceived(config_client_evt_model_app_list_t configModelAppList) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigModelAppListReceived(configModelAppList);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t subscriptionStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigSubscriptionStatusReceived(subscriptionStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t publicationStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigPublicationStatusReceived(publicationStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t beaconStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigBeaconStatusReceived(beaconStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t defaultTtlStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigDefaultTtlStatusReceived(defaultTtlStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t gattPorxyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigGattPorxyStatusReceived(gattPorxyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigKeyRefreshPhaseStatusReceived(keyRefreshPhaseStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigFriendStatusReceived(config_client_evt_friend_status_t friendStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigFriendStatusReceived(friendStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigRelayStatusReceived(config_client_evt_relay_status_t relayStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigRelayStatusReceived(relayStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigModelSubscriptionListReceived(config_client_evt_model_subscription_list_t modelSubscriptionList) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigModelSubscriptionListReceived(modelSubscriptionList);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigResetNodeStatusReceived(int status) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigResetNodeStatusReceived(status);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t nodeIdentityStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigNodeIdentityStatusReceived(nodeIdentityStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigHeartbeatPublicationStatusReceived(heartbeatPublicationStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigHeartbeatSubscriptionStatusReceived(heartbeatSubscriptionStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t networkTransmitStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigNetworkTransmitStatusReceived(networkTransmitStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshConfigurationModel.mMeshConfigurationModelListenerMgr.onMeshConfigLpnPollTimeoutStatusReceived(lpnPollTimeoutStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t genericOnOffStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericOnOffStatusReceived(genericOnOffStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t genericLevelStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericLevelStatusReceived(genericLevelStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t defaultTransitionTimeStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericDefaultTransitionTimeStatusReceived(defaultTransitionTimeStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t onPowerUpStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericOnPowerUpStatusReceived(onPowerUpStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t powerLevelStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericPowerLevelStatusReceived(powerLevelStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericPowerLastStatusReceived(ble_mesh_generic_client_evt_power_last_status_t powerLastStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericPowerLastStatusReceived(powerLastStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t powerDefaultStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericPowerDefaultStatusReceived(powerDefaultStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t powerRangeStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericPowerRangeStatusReceived(powerRangeStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericBatteryStatusReceived(ble_mesh_generic_client_evt_battery_status_t batteryStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericBatteryStatusReceived(batteryStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t locationGlobalStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericLocationGlobalStatusReceived(locationGlobalStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericLocationLocalStatusReceived(ble_mesh_generic_client_evt_local_location_status_t locationLocalStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericLocationLocalStatusReceived(locationLocalStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericManufacturerPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t manufacturerPropertiesStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericManufacturerPropertiesStatusReceived(manufacturerPropertiesStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericManufacturerPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t manufacturerPropertyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericManufacturerPropertyStatusReceived(manufacturerPropertyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericAdminPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t adminPropertiesStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericAdminPropertiesStatusReceived(adminPropertiesStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericAdminPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t adminPropertyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericAdminPropertyStatusReceived(adminPropertyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericUserPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t userPropertiesStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericUserPropertiesStatusReceived(userPropertiesStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericUserPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t userPropertyStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericUserPropertyStatusReceived(userPropertyStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshGenericClientPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t clientPropertiesStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshGenericOnOffModel.mMeshGenericOnOffModelListenerMgr.onMeshGenericClientPropertiesStatusReceived(clientPropertiesStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshHealthCurrentStatusReceived(ble_mesh_health_client_evt_current_status_t healthCurrentStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshHealthModel.mMeshHealthModelListenerMgr.onMeshHealthCurrentStatusReceived(healthCurrentStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t healthFaultStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshHealthModel.mMeshHealthModelListenerMgr.onMeshHealthFaultStatusReceived(healthFaultStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t healthPeriodStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshHealthModel.mMeshHealthModelListenerMgr.onMeshHealthPeriodStatusReceived(healthPeriodStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t healthAttentionStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshHealthModel.mMeshHealthModelListenerMgr.onMeshHealthAttentionStatusReceived(healthAttentionStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t lightnessStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightLightnessModel.mMeshLightingModelListenerMgr.onMeshLightnessStatusReceived(lightnessStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t linearLightnessStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightLightnessModel.mMeshLightingModelListenerMgr.onMeshLinearLightnessStatusReceived(linearLightnessStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLastLightnessStatusReceived(ble_mesh_lightness_client_evt_last_status_t lastLightnessStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightLightnessModel.mMeshLightingModelListenerMgr.onMeshLastLightnessStatusReceived(lastLightnessStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t defaultLightnessStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightLightnessModel.mMeshLightingModelListenerMgr.onMeshDefaultLightnessStatusReceived(defaultLightnessStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t rangeLightnessStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightLightnessModel.mMeshLightingModelListenerMgr.onMeshRangeLightnessStatusReceived(rangeLightnessStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t ctlStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightCTLModel.mMeshLightingModelListenerMgr.onMeshLightCtlStatusReceived(ctlStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t ctlTemperatureStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightCTLModel.mMeshLightingModelListenerMgr.onMeshLightCtlTemperatureStatusReceived(ctlTemperatureStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t ctlTemperatureRangeStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightCTLModel.mMeshLightingModelListenerMgr.onMeshLightCtlTemperatureRangeStatusReceived(ctlTemperatureRangeStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t ctlDefaultStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightCTLModel.mMeshLightingModelListenerMgr.onMeshLightCtlDefaultStatusReceived(ctlDefaultStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t hslStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightHSLModel.mMeshLightingModelListenerMgr.onMeshLightHslStatusReceived(hslStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t defaultHslStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightHSLModel.mMeshLightingModelListenerMgr.onMeshLightDefaultHslStatusReceived(defaultHslStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t hslRangeStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightHSLModel.mMeshLightingModelListenerMgr.onMeshLightHslRangeStatusReceived(hslRangeStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t hueStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightHSLModel.mMeshLightingModelListenerMgr.onMeshLightHueStatusReceived(hueStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t saturationStatus) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshLightHSLModel.mMeshLightingModelListenerMgr.onMeshLightSaturationStatusReceived(saturationStatus);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    @Override
    public void OnMeshVendorModelMsgReceived(ble_mesh_access_message_rx_t msg) {
        synchronized (this) {
            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_DONE;
            mMeshVendorModel.mMeshVendorModelListenerMgr.OnMeshVendorModelMsgReceived(msg);
            mSwitchState = SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
        }
    }

    class SUPPORTED_SERVICE {
        boolean MeshProvision;
        boolean MeshProxy;
    }

    class SearchAirohaMeshServiceThread extends Thread {
        final List<BluetoothGattService> mmGattServices;

        public SearchAirohaMeshServiceThread(List<BluetoothGattService> gattServices) {
            mmGattServices = gattServices;
        }

        @Override
        public void run() {
            try {
                Log.d(TAG, "findMeshServiceCharc...");
//                if(Build.VERSION.SDK_INT >= 21) {
//                    mAirohaLink.requestChangeMtu(mMTU);
//                    SystemClock.sleep(200);
//                }

                SUPPORTED_SERVICE tmp = findMeshServiceCharc(mmGattServices);
                Log.d(TAG, "isMeshProvisionSupported: " + tmp.MeshProvision);
                Log.d(TAG, "isMeshProxySupported: " + tmp.MeshProxy);

                if (tmp.MeshProvision || tmp.MeshProxy) {
                    getMeshCore().updateServiceState(true);
                    if (tmp.MeshProvision) {
                        mMeshServiceListenerMgr.onMeshProvisionServiceFound();
                    }
                    if (tmp.MeshProxy) {
                        mMeshServiceListenerMgr.onMeshProxyServiceFound();
                    }
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private SUPPORTED_SERVICE findMeshServiceCharc(List<BluetoothGattService> gattServices) {
        SUPPORTED_SERVICE ret = new SUPPORTED_SERVICE();
        boolean foundWriteCharc;
        boolean foundNotifyCharc;

        // Loops through available GATT Services.
        for (BluetoothGattService gattService : gattServices) {
            if (gattService.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROVISION_SERVICE_UUID)) {
                foundWriteCharc = false;
                foundNotifyCharc = false;
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                // Loops through available Characteristics.
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROVISION_SERVICE_DATA_IN_UUID)) {
                        foundWriteCharc = true;
                        // enable write
                        mMeshProvisionWriteCharc = gattCharacteristic;
                    } else if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROVISION_SERVICE_DATA_OUT_UUID)) {
                        // enable notif
                        if (true == mAirohaLink.setCharacteristicNotification(gattCharacteristic, true)) {
                            foundNotifyCharc = true;
                        } else {
                            Log.e(TAG, "Failed to enable notification for : MESH_PROVISION_SERVICE_DATA_OUT_UUID");
                        }
                    }
                }
                ret.MeshProvision = foundWriteCharc && foundNotifyCharc;
            } else if (gattService.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROXY_SERVICE_UUID)) {
                foundWriteCharc = false;
                foundNotifyCharc = false;
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattService.getCharacteristics();
                // Loops through available Characteristics.
                for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                    if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROXY_SERVICE_DATA_IN_UUID)) {
                        foundWriteCharc = true;
                        // enable write
                        mMeshProxyWriteCharc = gattCharacteristic;
                    } else if (gattCharacteristic.getUuid().toString().equalsIgnoreCase(AirohaMeshUUID.MESH_PROXY_SERVICE_DATA_OUT_UUID)) {
                        // enable notif
                        if (true == mAirohaLink.setCharacteristicNotification(gattCharacteristic, true)) {
                            foundNotifyCharc = true;
                        } else {
                            Log.e(TAG, "Failed to enable notification for : MESH_PROXY_SERVICE_DATA_OUT_UUID");
                        }
                    }
                }
                ret.MeshProxy = foundWriteCharc && foundNotifyCharc;
            }
        }

        return ret;
    }
}
