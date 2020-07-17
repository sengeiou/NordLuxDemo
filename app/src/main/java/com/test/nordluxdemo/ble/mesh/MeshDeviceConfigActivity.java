package com.test.nordluxdemo.ble.mesh;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airoha.libmesh.listener.MeshConfigurationModelListener;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.config.ble_mesh_composition_element_t;
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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.nordluxdemo.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class MeshDeviceConfigActivity extends BaseActivity implements MeshConfigurationModelListener {
    private final static String TAG = MeshDeviceConfigActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1;

    private MeshDeviceConfigActivity mContext;
    protected BluetoothLeService mBluetoothLeService = null;
    protected ServiceConnection mServiceConnection = null;
    protected Intent mServiceIntent = null;
    private boolean mConnected = false;

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private boolean mIsConnected = false;
    private boolean mIsProxyServiceDiscovered = false;
    private int mRetryDiscoverServices;

    ListView mLogView;
    TextView mConnectionState;
    Spinner mSpinnerSetGattProxyState;

    String mBtDeviceName;
    String mBtDeviceBdAddr;
    byte[] mUUID;
    short mUnicastAddr;
    int mIVIndex;
    byte[] mDeviceKey;
    short mNetkeyIndex;
    String mNetkeyName;
    byte[] mNetkey;

    ArrayList<Button> mBtnList;
    boolean mIsBackClicked = false;

    config_client_evt_composition_data_status_t mCompositionData;
    config_client_evt_appkey_status_t mConfigAppKeyStatus;
    ArrayList<config_client_evt_model_app_status_t> mConfigModelAppStatusList;

    ArrayList<MeshUtils.MODEL_INFO> mMeshModels;
    int mBindModelIndex;

    volatile boolean mIsAddingNetKey = false;
    volatile boolean mIsDeletingNetKey = false;

    @Override
    public void onGattConnected(BluetoothGatt gatt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsConnected = true;
                updateConnectionState(true, "Connected, discovering Service...");
                invalidateOptionsMenu();
                if (mHandler != null) {
                    mHandler.postDelayed(CheckDiscoverServiceTimeout, MeshUtils.mTimeoutDiscoverServices);
                }
            }
        });
    }

    @Override
    public void onGattDisconnected(BluetoothGatt gatt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsConnected = false;
                updateConnectionState(false, "Disconnected");
                invalidateOptionsMenu();
                setAllBtnState(false);
                if (mIsBackClicked) {
                    mContext.finish();
                }
            }
        });
    }

    @Override
    public void onMeshProxyServiceFound() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsProxyServiceDiscovered = true;
                updateConnectionState(true, "Connected, Proxy Service Found");
                addLogMsg("addConfigurationClient...");
                int ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().addConfigurationClient(Short.valueOf(MeshUtils.ELEMENT_IDNEX));
                if (0 >= ret) {
                    addLogMsg("Failed to addConfigurationClient");
                } else {
                    addLogMsg("addConfigurationClient OK");
                    setAllBtnState(true);
                }
            }
        });
    }

    @Override
    public void onMeshConfigCompositionReceived(config_client_evt_composition_data_status_t configComposition) {
        mCompositionData = configComposition;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateModelList(mCompositionData);
            }
        });
    }

    @Override
    public void onMeshConfigNetKeyStatusReceived(final config_client_evt_netkey_status_t configNetkeyStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_netkey_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigNetKeyStatusReceived: ";
                logMsg += gson.toJson(configNetkeyStatus, classType);
                addLogMsg(logMsg);

                if (configNetkeyStatus.status == 0) {
                    LinkedList<Short> tmp = MeshUtils.getPdInfo(mUnicastAddr).mNekworkIndexArray;
                    if (mIsAddingNetKey && !tmp.contains(configNetkeyStatus.netkey_index)) {
                        tmp.add(configNetkeyStatus.netkey_index);
                    }
                    if (mIsDeletingNetKey && tmp.contains(configNetkeyStatus.netkey_index)) {
                        tmp.remove(configNetkeyStatus.netkey_index);
                    }
                    mIsAddingNetKey = false;
                    mIsDeletingNetKey = false;
                    PreferenceUtility.savePdList(getApplicationContext(), MeshUtils.gPdInfoList);
                }
            }
        });
    }

    @Override
    public void onMeshConfigNetKeyListReceived(final config_client_evt_netkey_list_t configNetkeyList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_netkey_list_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigNetKeyListReceived: ";
                logMsg += gson.toJson(configNetkeyList, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t configAppkeyStatus) {
        mConfigAppKeyStatus = configAppkeyStatus;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_appkey_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigAppKeyStatusReceived: ";
                logMsg += gson.toJson(mConfigAppKeyStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigAppKeyListReceived(final config_client_evt_appkey_list_t configAppkeyList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_appkey_list_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigAppKeyListReceived: ";
                logMsg += gson.toJson(configAppkeyList, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigModelAppStatusReceived(final config_client_evt_model_app_status_t configModelAppStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_model_app_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigModelAppStatusReceived: ";
                logMsg += gson.toJson(configModelAppStatus, classType);
                addLogMsg(logMsg);

                mConfigModelAppStatusList.add(configModelAppStatus);
                mBindModelIndex += 1;

                if (mBindModelIndex < mMeshModels.size()) {
                    bindModel();
                }
            }
        });
    }

    @Override
    public void onMeshConfigModelAppListReceived(final config_client_evt_model_app_list_t configModelAppList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_model_app_list_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigModelAppListReceived: ";
                logMsg += gson.toJson(configModelAppList, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigPublicationStatusReceived(final config_client_evt_model_publication_status_t publicationStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_model_publication_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigPublicationStatusReceived: ";
                logMsg += gson.toJson(publicationStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigSubscriptionStatusReceived(final config_client_evt_model_subscription_status_t subscriptionStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_model_subscription_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigSubscriptionStatusReceived: ";
                logMsg += gson.toJson(subscriptionStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigBeaconStatusReceived(final config_client_evt_beacon_status_t beaconStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_beacon_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigBeaconStatusReceived: ";
                logMsg += gson.toJson(beaconStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigDefaultTtlStatusReceived(final config_client_evt_default_ttl_status_t defaultTtlStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_default_ttl_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigDefaultTtlStatusReceived: ";
                logMsg += gson.toJson(defaultTtlStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigGattPorxyStatusReceived(final config_client_evt_gatt_proxy_status_t gattPorxyStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_gatt_proxy_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigGattPorxyStatusReceived: ";
                logMsg += gson.toJson(gattPorxyStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigKeyRefreshPhaseStatusReceived(final config_client_evt_key_refresh_phase_status_t keyRefreshPhaseStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_key_refresh_phase_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigKeyRefreshPhaseStatusReceived: ";
                logMsg += gson.toJson(keyRefreshPhaseStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigFriendStatusReceived(final config_client_evt_friend_status_t friendStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_friend_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigFriendStatusReceived: ";
                logMsg += gson.toJson(friendStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigRelayStatusReceived(final config_client_evt_relay_status_t relayStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_relay_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigRelayStatusReceived: ";
                logMsg += gson.toJson(relayStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigModelSubscriptionListReceived(final config_client_evt_model_subscription_list_t modelSubscriptionList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_model_subscription_list_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigModelSubscriptionListReceived: ";
                logMsg += gson.toJson(modelSubscriptionList, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigNodeIdentityStatusReceived(final config_client_evt_node_identity_status_t nodeIdentityStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_node_identity_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigNodeIdentityStatusReceived: ";
                logMsg += gson.toJson(nodeIdentityStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigHeartbeatPublicationStatusReceived(final config_client_evt_heartbeat_publication_status_t heartbeatPublicationStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_heartbeat_publication_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigHeartbeatPublicationStatusReceived: ";
                logMsg += gson.toJson(heartbeatPublicationStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigHeartbeatSubscriptionStatusReceived(final config_client_evt_heartbeat_subscription_status_t heartbeatSubscriptionStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_heartbeat_subscription_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigHeartbeatSubscriptionStatusReceived: ";
                logMsg += gson.toJson(heartbeatSubscriptionStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigNetworkTransmitStatusReceived(final config_client_evt_network_transmit_status_t networkTransmitStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_network_transmit_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigNetworkTransmitStatusReceived: ";
                logMsg += gson.toJson(networkTransmitStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigLpnPollTimeoutStatusReceived(final config_client_evt_lpn_poll_timeout_status_t lpnPollTimeoutStatus) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type classType = new TypeToken<config_client_evt_lpn_poll_timeout_status_t>() {
                }.getType();
                Gson gson = new Gson();
                String logMsg = "onMeshConfigLpnPollTimeoutStatusReceived: ";
                logMsg += gson.toJson(lpnPollTimeoutStatus, classType);
                addLogMsg(logMsg);
            }
        });
    }

    @Override
    public void onMeshConfigResetNodeStatusReceived(final int status) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addLogMsg("RESET_NODE_STATUS_RECEIVED: status = " + String.valueOf(status));

                if (status == 0) {
                    MeshUtils.removePdInfo(mBtDeviceBdAddr);
                    PreferenceUtility.savePdList(getApplicationContext(), MeshUtils.gPdInfoList);

                    mContext.setResult(MeshUtils.RESET_NODE_RESULT_CODE, null);
                    mContext.finish();
                }
            }
        });
    }

    private Runnable CheckDiscoverServiceTimeout = new Runnable() {
        @Override
        public void run() {
            if (mIsProxyServiceDiscovered || !mIsConnected) {
                return;
            }
            if (mBluetoothLeService == null) {
                addLogMsg("mBluetoothLeService is null.");
                return;
            }
            if (mRetryDiscoverServices < MeshUtils.mRetryDiscoverServices) {
                mRetryDiscoverServices++;
                mBluetoothLeService.getAirohaLink().discoverServices();
                addLogMsg("Discover Service Timeout(" + MeshUtils.mTimeoutDiscoverServices + "ms), Start Retry (RetryCount: " + mRetryDiscoverServices + " times)");
                if (mHandler != null) {
                    mHandler.postDelayed(CheckDiscoverServiceTimeout, MeshUtils.mTimeoutDiscoverServices);
                }
            } else {
                mRetryDiscoverServices = 0;
                mBluetoothLeService.disconnect();
                showAlertDialog(MeshDeviceConfigActivity.this, "Error", "Discover Service Timeout, RetryCount: " + MeshUtils.mRetryDiscoverServices + " times");
            }
        }
    };

    private void showAlertDialog(final Context context, String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    class myServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) (service)).getService();
            updateConnectionState(false, "Connecting");

            if (mBluetoothLeService != null) {
                mBluetoothLeService.getAirohaLink().addGattStateListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshParam().addListener(mContext.getLocalClassName(), mContext);

                mBluetoothLeService.connect(mBtDeviceBdAddr);
            }

            mIsServiceConnected = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mServiceConnection = null;
            mBluetoothLeService = null;
            mIsServiceConnected = false;
            MeshDeviceConfigActivity.this.finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_config_ut_activity);
        //getSupportActionBar().setTitle("Config UT");
        mContext = this;
        mServiceIntent = new Intent(this, BluetoothLeService.class);
        mBtnList = new ArrayList<>();
        mMeshModels = new ArrayList<>();
        mConfigModelAppStatusList = new ArrayList<>();

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
        }

        ArrayAdapter<String> tmp = new ArrayAdapter<>(this, R.layout.message);
        if (MeshUtils.gLogAdapter != null) {
            for (int i = 0; i < MeshUtils.gLogAdapter.getCount(); ++i) {
                tmp.add(MeshUtils.gLogAdapter.getItem(i));
            }
        }
        MeshUtils.gLogAdapter = tmp;
        mLogView = (ListView) findViewById(R.id.listView_log);
        mLogView.setAdapter(MeshUtils.gLogAdapter);

        final Intent intent = getIntent();
        mBtDeviceName = intent.getStringExtra(MeshUtils.EXTRAS_BT_NAME);
        mBtDeviceBdAddr = intent.getStringExtra(MeshUtils.EXTRAS_BT_BD_ADDR);
        mUUID = MeshUtils.DEV_UUID;
        mIVIndex = MeshUtils.gIvIndex;
        mUnicastAddr = intent.getShortExtra(MeshUtils.EXTRAS_UNICAST_ADDR, (short) 0);
        mDeviceKey = intent.getByteArrayExtra(MeshUtils.EXTRAS_DEVICE_KEY);

        mNetkeyIndex = intent.getShortExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, MeshUtils.NET_KEY_INDEX);
        MeshUtils.NETKEY_INFO netkeyInfo = MeshUtils.getNetkeyInfo(mNetkeyIndex);
        if (netkeyInfo != null) {
            mNetkey = netkeyInfo.mNetKey;
            mNetkeyName = netkeyInfo.mNetworkName;
        } else {
            mNetkey = MeshUtils.NET_KEY;
            mNetkeyName = MeshUtils.NET_NAME;
        }

        addLogMsg("Device Name: " + mBtDeviceName);
        addLogMsg("Device Unicast Address: " + String.valueOf(mUnicastAddr));
        addLogMsg("Network Index: " + String.valueOf(mNetkeyIndex));
        addLogMsg("Network Name: " + mNetkeyName);

        mConnectionState = (TextView) findViewById(R.id.connection_state);

        setupGetComposition();



        setupConfigAddAppKey();


        setupConfigBindModelApp();
        proxyState();

    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler = new Handler();
        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            setAllBtnState(false);
            mServiceConnection = new myServiceConnection();
            addLogMsg("Connecting App Service...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mIsServiceConnected = false;
                    bindService(
                            mServiceIntent,
                            mServiceConnection,
                            Context.BIND_NOT_FOREGROUND);
                }
            }, DELAY_MS_FOR_CONNECT);
        }
        mIsConnected = false;
        mIsProxyServiceDiscovered = false;
        mRetryDiscoverServices = 0;
    }

    @Override
    protected void onPause() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.getAirohaLink().removeGattStateListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshParam().removeListener(mContext.getLocalClassName());
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
        super.onBackPressed();
        Log.d(TAG, "onBackPressed()");
        mIsBackClicked = true;
    }

    void proxyState() {
        mSpinnerSetGattProxyState = findViewById(R.id.spinner_config_set_gatt_proxy_state);
        mSpinnerSetGattProxyState.setAdapter(
                new ArrayAdapter<>(
                        this, R.layout.spinner_item, ENUM_DEF.ble_mesh_feature_state_t.values()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gatt_services, menu);
        if (mConnected) {
            menu.findItem(R.id.menu_connect).setVisible(false);
            menu.findItem(R.id.menu_disconnect).setVisible(true);
        } else {
            menu.findItem(R.id.menu_connect).setVisible(true);
            menu.findItem(R.id.menu_disconnect).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_connect:
                updateConnectionState(false, "Connecting");
                mBluetoothLeService.connect(mBtDeviceBdAddr);
                return true;
            case R.id.menu_disconnect:
                mBluetoothLeService.disconnect();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateConnectionState(boolean isConnected, final String state) {
        mConnected = isConnected;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addLogMsg(state);
                mConnectionState.setText(state);
            }
        });
        invalidateOptionsMenu();
    }

    void addLogMsg(String msg) {
        Log.d(TAG, msg);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS    ");
        String timeStr = sdf.format(new Date());
        MeshUtils.gLogAdapter.add(timeStr + msg);
    }

    void setAllBtnState(final boolean isEnabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Button btn : mBtnList) {
                    btn.setEnabled(isEnabled);
                }
            }
        });
    }

    void setupGetComposition() {
        Button btn = (Button) findViewById(R.id.btn_config_get_composition_data);
        mBtnList.add(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().setNetKeyIndex(mNetkeyIndex);
                if (ret == 0) {
                    ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().getComposition(mUnicastAddr);
                    addLogMsg("get_composition_data");
                }
                if (ret != 0) {
                    addLogMsg("Failed to meshGetComposition: " + String.valueOf(ret));
                }
            }
        });
    }


    void setupConfigAddAppKey() {
        Button btn = (Button) findViewById(R.id.btn_config_add_app_key);
        mBtnList.add(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().addConfigAppKey(mUnicastAddr, mNetkeyIndex, MeshUtils.APP_KEY_INDEX, MeshUtils.APP_KEY);
                addLogMsg("add_app_key");
                if (ret != 0) {
                    addLogMsg("Failed to exec add_app_key: " + String.valueOf(ret));
                }
            }
        });
    }


    void setupConfigBindModelApp() {
        Button btn = (Button) findViewById(R.id.btn_config_bind_model_app);
        mBtnList.add(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mConfigModelAppStatusList.clear();
                mBindModelIndex = 0;
                bindModel();
            }
        });
    }


    private void updateModelList(config_client_evt_composition_data_status_t compositionData) {
        if (compositionData == null) {
            return;
        }

        String full_unicastAddr = MeshUtils.getFullAddrStr(mUnicastAddr);
        MeshUtils.PD_INFO mPdInfo = MeshUtils.getPdInfo(mUnicastAddr);
        LinkedList<String> elements_list = mPdInfo.mPrimaryElementsMap.get(full_unicastAddr);
        if (elements_list == null) {
            elements_list = new LinkedList<String>();
        }
        mPdInfo.mElementModelsMap.clear();

        String logMsg = "CONFIG_COMPOSITION_RECEIVED: ";
        mMeshModels.clear();
        short elemetID = 0;
        for (ble_mesh_composition_element_t element : compositionData.composition_data.elements) {
            MeshUtils.MODEL_INFO tmp;
            for (short sModel : element.Sig_models) {
                tmp = new MeshUtils.MODEL_INFO(elemetID, (int) sModel & 0x0000FFFF);
                String full_addr = getElementFullAddr(mUnicastAddr, elemetID);
                if (!elements_list.contains(full_addr)) {
                    elements_list.add(full_addr);
                }

                if (mPdInfo.mElementModelsMap.containsKey(full_addr)) {
                    mPdInfo.mElementModelsMap.get(full_addr).add(MeshUtils.getFullAddrStr(sModel));
                } else {
                    LinkedList<String> models_list = new LinkedList<String>();
                    models_list.add(MeshUtils.getFullAddrStr(sModel));
                    mPdInfo.mElementModelsMap.put(full_addr, models_list);
                }
                mMeshModels.add(tmp);
                logMsg += "\nelemetID = " + tmp.mHexElementID + ", sig_modelID = " + tmp.mHexModelID;
            }
            for (int vModel : element.vendor_models) {
                tmp = new MeshUtils.MODEL_INFO(elemetID, vModel);
                String full_addr = getElementFullAddr(mUnicastAddr, elemetID);
                if (!elements_list.contains(full_addr)) {
                    elements_list.add(full_addr);
                }
                mMeshModels.add(tmp);
                logMsg += "\nelemetID = " + tmp.mHexElementID + ", vendor_modelID = " + tmp.mHexModelID;
            }
            elemetID += 1;
        }
        mPdInfo.mPrimaryElementsMap.put(full_unicastAddr, elements_list);
        PreferenceUtility.savePdList(getApplicationContext(), MeshUtils.gPdInfoList);


        addLogMsg(logMsg);
    }

    private String getElementFullAddr(short primary_addr, short element_addr) {
        String Addr = Integer.toHexString(primary_addr + element_addr);
        String full_addr = "";
        for (int i = 0; i < 4 - Addr.length(); ++i) {
            full_addr += "0";
        }
        full_addr += Addr.toUpperCase();
        return full_addr;
    }

    private void bindModel() {
        if (mMeshModels.size() == 0) {
            addLogMsg("Failed to exec bind_model_app : no model found");
            return;
        }

        MeshUtils.MODEL_INFO modelInfo = mMeshModels.get(mBindModelIndex);
        short elementAddr = (short) (mUnicastAddr + modelInfo.mElementID);

        int ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().setNetKeyIndex(mNetkeyIndex);
        if (ret == 0) {
            ret = mBluetoothLeService.getAirohaMeshMgr().getMeshConfigurationModel().bindConfigModelApp(mUnicastAddr, elementAddr, MeshUtils.APP_KEY_INDEX, modelInfo.mModelID);
            addLogMsg("bind_model_app");
        }
        if (ret != 0) {
            addLogMsg("Failed to exec bind_model_app  for UnicastAddr:" + MeshUtils.shortToHexString(mUnicastAddr)
                    + ", Model ID: " + MeshUtils.intToHexString(modelInfo.mModelID) + ", ret = " + String.valueOf(ret));
        }
    }


}
