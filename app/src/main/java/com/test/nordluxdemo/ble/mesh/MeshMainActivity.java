package com.test.nordluxdemo.ble.mesh;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.airoha.libmesh.listener.MeshConfigurationModelListener;
import com.airoha.libmesh.listener.MeshProvisionListener;
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
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;
import com.test.nordluxdemo.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MeshMainActivity extends BaseActivity implements MeshConfigurationModelListener, MeshProvisionListener {
    private final static String TAG = "Airoha_" + MeshMainActivity.class.getSimpleName();

    private MeshMainActivity mContext;
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ENABLE_GPS = 2;
    private static final int REQUEST_PERMISSION_RETURN_CODE = 5566;

    private Button mBtnNetwork;
    private Button mBtnScan;
    private Button mBtnAutoProvision;

    protected ArrayList<String> mPermissionList = null;
    protected BluetoothLeService mBluetoothLeService = null;
    protected ServiceConnection mServiceConnection = null;
    protected Intent mServiceIntent = null;

    private String mNetworkIndex;
    private DeviceInfo mTargetDevice;
    private DeviceInfo mDevice;
    private ConcurrentHashMap<String, List<DeviceInfo>> mResetDeviceMap;
    private List<String> mScanProvisionDevice;

    private boolean isReseting = false;

    private Handler mResetHandler;
    private List<DeviceInfo> mFailResetDevices;
    private List<DeviceInfo> mSuccessResetDevices;
    private boolean mIsConnected;
    private boolean mIsConnectTimeout;

    private boolean isInitialized = false;
    private int mClickCountOnActionBar;


    private boolean mScanning = false;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mBluetoothLeScanner;

    @Override
    public void onGattConnected(BluetoothGatt gatt) {
        super.onGattConnected(gatt);
    }

    @Override
    public void onGattDisconnected(BluetoothGatt gatt) {
        super.onGattDisconnected(gatt);
    }

    @Override
    public void onMeshProxyServiceFound() {
        super.onMeshProxyServiceFound();
    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_main_activity);
        mContext = this;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }

        //setClickListenerOnActionBar();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "ble_not_supported", Toast.LENGTH_SHORT).show();
        }

        mServiceIntent = new Intent(this, BluetoothLeService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(mServiceIntent);
        } else {
            startService(mServiceIntent);
        }

        setContentView(R.layout.mesh_main_activity);

        mBtnNetwork = (Button) this.findViewById(R.id.btnMeshMainNetwork);
        mBtnNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MeshMainActivity.this, MeshNetworkListActivity.class);
                startActivity(intent);
            }
        });
        mBtnScan = (Button) this.findViewById(R.id.btnMeshMainScan);
        mBtnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MeshMainActivity.this, MeshScanActivity.class);
                startActivity(intent);
            }
        });



        mPermissionList = new ArrayList<>();
        mPermissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        mPermissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        checkPermission();

        MeshUtils.gIvIndex = PreferenceUtility.getMeshIvIndex(getApplicationContext());
        MeshUtils.gNetkeyInfoList = PreferenceUtility.getNetkeyList(getApplicationContext());
        MeshUtils.gGroupInfoList = PreferenceUtility.getGroupList(getApplicationContext());
        MeshUtils.gPdInfoList = PreferenceUtility.getPdList(getApplicationContext());
        MeshUtils.gLogAdapter = new ArrayAdapter<>(this, R.layout.message);

    }
    @Override
    protected void onResume() {
        super.onResume();
//        gpsStatusCheck();

        mResetDeviceMap = new ConcurrentHashMap<>();
        mFailResetDevices = new ArrayList<>();
        mSuccessResetDevices = new ArrayList<>();
        mResetHandler = new Handler();
        mServiceConnection = new myServiceConnection();
        mIsServiceConnected = false;
        bindService(
                mServiceIntent,
                mServiceConnection,
                Context.BIND_NOT_FOREGROUND);
        mClickCountOnActionBar = 0;
    }

    @Override
    protected void onPause() {
        if(mResetHandler != null){
            mResetHandler.removeCallbacksAndMessages(null);
            mResetHandler = null;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.getAirohaLink().removeGattStateListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshParam().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().removeListener(mContext.getLocalClassName());
            unbindService(mServiceConnection);
            mIsServiceConnected = false;
            mBluetoothLeService = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mBluetoothLeService != null) {
            unbindService(mServiceConnection);
            mIsServiceConnected = false;
            mBluetoothLeService = null;
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed()");
        if(mBluetoothLeService != null) {
            mBluetoothLeService.getAirohaMeshMgr().destroy();
        }
        stopService(mServiceIntent);
        this.finish();
    }

    @Override
    public void onMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition) {

    }

    @Override
    public void onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t configNetkeyStatus) {

    }

    @Override
    public void onMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t configNetkeyList) {

    }

    @Override
    public void onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus) {

    }

    @Override
    public void onMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t configAppkeyList) {

    }

    @Override
    public void onMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t configModelAppStatus) {

    }

    @Override
    public void onMeshConfigModelAppListReceived(config_client_evt_model_app_list_t configModelAppList) {

    }

    @Override
    public void onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t subscriptionStatus) {

    }

    @Override
    public void onMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t publicationStatus) {

    }

    @Override
    public void onMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t beaconStatus) {

    }

    @Override
    public void onMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t defaultTtlStatus) {

    }

    @Override
    public void onMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t gattPorxyStatus) {

    }

    @Override
    public void onMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus) {

    }

    @Override
    public void onMeshConfigFriendStatusReceived(config_client_evt_friend_status_t friendStatus) {

    }

    @Override
    public void onMeshConfigRelayStatusReceived(config_client_evt_relay_status_t relayStatus) {

    }

    @Override
    public void onMeshConfigModelSubscriptionListReceived(config_client_evt_model_subscription_list_t modelSubscriptionList) {

    }

    @Override
    public void onMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t nodeIdentityStatus) {

    }

    @Override
    public void onMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus) {

    }

    @Override
    public void onMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus) {

    }

    @Override
    public void onMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t networkTransmitStatus) {

    }

    @Override
    public void onMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus) {

    }

    @Override
    public void onMeshConfigResetNodeStatusReceived(final int status) {

    }

    @Override
    public void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {
        mScanProvisionDevice.add(device.getAddress());
    }

    @Override
    public void onMeshProvCapReceived(ble_mesh_prov_capabilities_t provCap) {
    }

    @Override
    public void onMeshProvStateChanged(boolean state, byte[] deviceKey, short address) {

    }

    @Override
    public void onMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response provAliResp) {

    }

    @Override
    public void onMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice) {

    }
    class myServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG,"onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) (service)).getService();
            if (mBluetoothLeService != null) {
                mBluetoothLeService.getAirohaLink().addGattStateListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshParam().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().addListener(mContext.getLocalClassName(), mContext);
            }

            if (isInitialized) {
                return;
            }

            boolean is_first_run_app = PreferenceUtility.getFirstRunAppStatus(getApplicationContext());
            int ret = MeshUtils.initMesh(mBluetoothLeService, is_first_run_app);

            if (ret != 0) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MeshMainActivity.this, "failed to init AirohaMesh", Toast.LENGTH_SHORT);
                    }
                });
            } else {
                PreferenceUtility.saveFirstRunAppStatus(getApplicationContext(), false);
                isInitialized = true;
            }

            mIsServiceConnected = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
            mServiceConnection = null;
            mBluetoothLeService = null;
            mIsServiceConnected = false;
            MeshMainActivity.this.finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    protected boolean checkPermission() {
        boolean ret = true;

        for (String permission : mPermissionList) {
            int permissionCheck = ActivityCompat.checkSelfPermission(this, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        this,
                        mPermissionList.toArray(new String[0]),
                        REQUEST_PERMISSION_RETURN_CODE);
                ret = false;
                break;
            }
        }

        return ret;
    }
    void addLogMsg(String msg) {
        Log.d(TAG, msg);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS    ");
        String timeStr = sdf.format(new Date());
        MeshUtils.gLogAdapter.add(timeStr + msg);
    }
    class doConnect implements Runnable {
        private String _device_addr;
        public doConnect(String addr) {
            _device_addr = addr;
        }

        public void run() {
            mBluetoothLeService.connect(_device_addr);
            addLogMsg("connecting to " + _device_addr);
            mIsConnectTimeout = false;
            if(mResetHandler != null) {
                mResetHandler.postDelayed(new checkDeviceConnectionTimeout(mNetworkIndex), 5000);
            }
        }
    }
    private final class checkDeviceConnectionTimeout implements Runnable{
        private String _NetworkIndex;
        public checkDeviceConnectionTimeout(String networkIndex) {
            this._NetworkIndex = networkIndex;
        }

        public void run() {
            if(mResetDeviceMap.containsKey(_NetworkIndex)) {
                if (!mIsConnected) {
                    mIsConnectTimeout = true;
                    isReseting = false;
                    Log.d(TAG, "Reset flow is stopped");

                    for (DeviceInfo item : mResetDeviceMap.get(mNetworkIndex)) {
                        mFailResetDevices.add(item);
                    }
                    mResetDeviceMap.remove(mNetworkIndex);
                    //checkResetResult();
                }
            }
        }
    }

    private void connectNextNetworkDevice() {
        if (mResetDeviceMap.size() == 0) {
            return;
        }
        ConcurrentHashMap.Entry<String, List<DeviceInfo>> entry = mResetDeviceMap.entrySet().iterator().next();
        if (entry != null) {
            int target_num = (int) (Math.random() * entry.getValue().size());
            mTargetDevice = entry.getValue().get(target_num);
            mNetworkIndex = entry.getKey();

            if(mResetHandler != null) {
                mResetHandler.postDelayed(new doConnect(mTargetDevice._deviceBtAddr), 0);
            }
        }
    }
    class DeviceInfo{
        String _primaryAddr;
        String _deviceName;
        String _deviceBtAddr;

        public DeviceInfo(String primaryAddr, String deviceName, String deviceBtAddr){
            _primaryAddr = primaryAddr;
            _deviceName = deviceName;
            _deviceBtAddr = deviceBtAddr;
        }
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                    if (mBluetoothLeService != null) {
                        mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().parseMeshADV(device, rssi, scanRecord);
                    }
                }
            };

    private ScanCallback mScanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.i(TAG, result.toString());
            List<ParcelUuid> uuids = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                uuids = result.getScanRecord().getServiceUuids();
            }
            if (uuids != null && mBluetoothLeService != null) {
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().parseMeshScanResult(result);
            }
        }
    };


}
