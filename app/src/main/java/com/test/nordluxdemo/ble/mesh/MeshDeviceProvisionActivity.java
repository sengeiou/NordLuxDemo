package com.test.nordluxdemo.ble.mesh;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.airoha.libmesh.listener.MeshProvisionListener;
import com.airoha.libmeshparam.PROV_INPUT_DATA;
import com.airoha.libmeshparam.model.config.config_client_evt_appkey_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_composition_data_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_app_status_t;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.nordluxdemo.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class MeshDeviceProvisionActivity extends BaseActivity implements MeshProvisionListener {
    private final static String TAG = MeshDeviceProvisionActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1;
    private static final int ENTER_CONFIG_PAGE = 2;

    private MeshDeviceProvisionActivity mContext;
    protected BluetoothLeService mBluetoothLeService = null;
    protected ServiceConnection mServiceConnection = null;
    protected Intent mServiceIntent = null;
    private boolean mConnected = false;

    private BluetoothAdapter mBluetoothAdapter;
    private Handler mHandler;
    private boolean mIsConnected = false;
    private boolean mIsProvisionServiceDiscovered = false;
    private int mRetryDiscoverServices;

    ListView mLogView;
    TextView mConnectionState;

    @Override
    public void onGattConnected(BluetoothGatt gatt) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsConnected = true;
                updateConnectionState(true, "Connected, discovering Service...");
                invalidateOptionsMenu();
                if(mHandler != null) {
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
                    MeshDeviceProvisionActivity.this.finish();
                }
            }
        });
    }

    @Override
    public void onMeshProvisionServiceFound() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mIsProvisionServiceDiscovered = true;
                updateConnectionState(true, "Connected, Provision Service Found");
                setAllBtnState(true);
            }
        });
    }

    @Override
    public void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {
    }

    @Override
    public void onMeshProvCapReceived(final ble_mesh_prov_capabilities_t provCap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mNumElements = provCap.number_of_elements;

                Type listType = new TypeToken<ble_mesh_prov_capabilities_t>(){}.getType();
                Gson gson = new Gson();
                addLogMsg("onMeshProvCapReceived: " + gson.toJson(provCap, listType));
            }
        });
    }

    @Override
    public void onMeshProvStateChanged(final boolean state, final byte[] deviceKey, final short addr) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProvState = ProvState.PROV_STATE_DONE;
                String logMsg = "";
                logMsg += "onMeshProvStateChanged";
                logMsg += "\nstatus = " + state;
                logMsg += "\nunicast_addr = " + MeshUtils.shortToHexString(addr);
                logMsg += "\ndevice_key = " + MeshUtils.bytesToHexString(deviceKey);
                logMsg += "\nbd_addr = " + mBtDeviceBdAddr;
                if (addr != mUnicastAddr) {
                    logMsg += "\nError: the unicast_addr doesn't match the target UnicastAddr: "
                            + MeshUtils.shortToHexString(mUnicastAddr);
                    showAlertDialog(MeshDeviceProvisionActivity.this, "Error", "The unicast_addr doesn't match the target UnicastAddr:"+ MeshUtils.shortToHexString(mUnicastAddr));
                    setAllBtnState(true);
                } else if (state) {
                    mDeviceKey = deviceKey;
                    PreferenceUtility.saveClientAddrCounter(getApplicationContext(),
                            addr + mNumElements, MeshUtils.AddrType.UNICAST);
                    Log.d(TAG, "saveClientAddrCounter:" + MeshUtils.getFullAddrStr(addr + mNumElements));

                    MeshUtils.PD_INFO pdInfo = new MeshUtils.PD_INFO();
                    pdInfo.mNekworkIndexArray = new LinkedList<>();
                    pdInfo.mNekworkIndexArray.add(mNetkeyIndex);
                    pdInfo.mModelGroupMap = new HashMap<>();
                    pdInfo.mPrimaryElementsMap = new HashMap<>();
                    pdInfo.mElementModelsMap = new HashMap<>();
                    pdInfo.mDeviceName = mBtDeviceName == null ? "Unknown":mBtDeviceName;
                    pdInfo.mDeviceBdAddr = mBtDeviceBdAddr;
                    pdInfo.mDeviceUUID = mUUID;
                    pdInfo.mUnicastAddr = addr;
                    pdInfo.mDeviceKey = mDeviceKey;
                    MeshUtils.gPdInfoList.add(pdInfo);

                    for (MeshUtils.PD_INFO pd : MeshUtils.gPdInfoList){
                        if(pd.mDeviceBdAddr.equalsIgnoreCase(mBtDeviceBdAddr)){
                            Log.d(TAG, "Provision error: duplicate bd address " + mBtDeviceBdAddr);
                            break;
                        }
                    }
                    PreferenceUtility.savePdList(getApplicationContext(), MeshUtils.gPdInfoList);
                    showConfigDialog(MeshDeviceProvisionActivity.this, "Config", "Continue to Config?");
                }
                else{
                    logMsg += "\nError: Status error.";
                    showAlertDialog(MeshDeviceProvisionActivity.this, "Error", "Status Error.");
                }
                addLogMsg(logMsg);
            }
        });
    }


    @Override
    public void onMeshAliProvisioningResponse(final ble_mesh_evt_prov_ali_response provAliResp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<ble_mesh_evt_prov_ali_response>(){}.getType();
                Gson gson = new Gson();
                addLogMsg("onMeshAliProvisioningResponse: " + gson.toJson(provAliResp, listType));

                byte[] text = new byte[32];
                Arrays.fill(text, (byte)0x00);
                System.arraycopy(provAliResp.provisioner_random, 0, text, 0, 16);

                byte[] out = MeshCrypto.aesCmac(provAliResp.confirmation_key, text);

                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().aliProvConfirmation(out);
            }
        });
    }

    @Override
    public void onMeshAliProvisioningConfirmationDevice(final ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<ble_mesh_evt_prov_ali_confirmation_device>(){}.getType();
                Gson gson = new Gson();
                addLogMsg("onMeshAliProvisioningConfirmationDevice: " + gson.toJson(provAliConfirmDevice, listType));

                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().aliProvProvideAuthenticationResult(true);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENTER_CONFIG_PAGE) {
            finish();
            return;
        }
    }

    private Runnable CheckDiscoverServiceTimeout = new Runnable() {
        @Override
        public void run() {
            if(mIsProvisionServiceDiscovered || !mIsConnected){
                return;
            }
            if(mBluetoothLeService == null){
                addLogMsg("mBluetoothLeService is null.");
                return;
            }
            if(mRetryDiscoverServices < MeshUtils.mRetryDiscoverServices){
                mRetryDiscoverServices++;
                mBluetoothLeService.getAirohaLink().discoverServices();
                addLogMsg("Discover Service Timeout(" + MeshUtils.mTimeoutDiscoverServices + "ms), Start Retry (RetryCount: " + mRetryDiscoverServices + " times)");

                if(mHandler != null) {
                    mHandler.postDelayed(CheckDiscoverServiceTimeout, MeshUtils.mTimeoutDiscoverServices);
                }
            }
            else {
                mRetryDiscoverServices = 0;
                mBluetoothLeService.disconnect();
                showAlertDialog(MeshDeviceProvisionActivity.this, "Error", "Discover Service Timeout, RetryCount: " + MeshUtils.mRetryDiscoverServices + " times");
            }
        }
    };

    private enum ProvState {
        PROV_STATE_IDLE,
        PROV_STATE_START,
        PROV_STATE_DONE
    };

    private ProvState mProvState = ProvState.PROV_STATE_IDLE;

    String mBtDeviceName;
    String mBtDeviceBdAddr;
    byte[] mNetworkKey;
    short mNetkeyIndex;
    byte[] mUUID;
    short mUnicastAddr;
    int mIVIndex;
    byte[] mDeviceKey;
    int mNumElements;

    ArrayList<Button> mBtnList;
    boolean mIsBackClicked = false;

    config_client_evt_composition_data_status_t mCompositionData;
    config_client_evt_appkey_status_t mConfigAppKeyStatus;
    ArrayList<config_client_evt_model_app_status_t> mConfigModelAppStatusList;

    ArrayList<MeshUtils.MODEL_INFO> mMeshModels;
    int mBindModelIndex;

    class myServiceConnection implements ServiceConnection {

        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) (service)).getService();
            updateConnectionState(false, "Connecting");

            if (mBluetoothLeService != null) {
                mBluetoothLeService.getAirohaLink().addGattStateListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.getAirohaMeshMgr().getMeshParam().addListener(mContext.getLocalClassName(), mContext);
                mBluetoothLeService.connect(mBtDeviceBdAddr);
            }

            mIsServiceConnected = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG,"onServiceDisconnected");
            mServiceConnection = null;
            mBluetoothLeService = null;
            mIsServiceConnected = false;
            MeshDeviceProvisionActivity.this.finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_provision_ut_activity);
       // getSupportActionBar().setTitle("Provision UT");
        mContext = this;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
        }

        mServiceIntent = new Intent(this, BluetoothLeService.class);

        final Intent intent = getIntent();
        mBtDeviceName = intent.getStringExtra(MeshUtils.EXTRAS_BT_NAME);
        mBtDeviceBdAddr = intent.getStringExtra(MeshUtils.EXTRAS_BT_BD_ADDR);
        mUUID = intent.getByteArrayExtra(MeshUtils.EXTRAS_DEVICE_UUID);
        mNetkeyIndex = MeshUtils.NET_KEY_INDEX;
        mNetworkKey = MeshUtils.NET_KEY;
        mIVIndex = MeshUtils.gIvIndex;


        mConnectionState = (TextView) findViewById(R.id.connection_state);

        setLogMsg();

        mBtnList = new ArrayList<>();

        setupInviteStartProvision();
        setupAliInviteProvision();
    }


    private void setLogMsg() {
        ArrayAdapter<String> tmp = new ArrayAdapter<>(this, R.layout.message);
        if(MeshUtils.gLogAdapter != null) {
            for (int i = 0; i < MeshUtils.gLogAdapter.getCount(); ++i) {
                tmp.add(MeshUtils.gLogAdapter.getItem(i));
            }
        }
        MeshUtils.gLogAdapter = tmp;
        mLogView = (ListView) findViewById(R.id.listView_log);
        mLogView.setAdapter(MeshUtils.gLogAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler = new Handler();
        setLogMsg();
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            mUnicastAddr = (short)PreferenceUtility.getClientAddrCounter(getApplicationContext(), MeshUtils.AddrType.UNICAST);
            Log.d(TAG, "getClientAddrCounter:" + MeshUtils.getFullAddrStr(mUnicastAddr));
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
        mIsProvisionServiceDiscovered = false;
        mRetryDiscoverServices = 0;
    }

    @Override
    protected void onPause() {
        if(mHandler != null){
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.disconnect();
            mBluetoothLeService.getAirohaLink().removeGattStateListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().removeListener(mContext.getLocalClassName());
            mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().removeListener(mContext.getLocalClassName());
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
        switch(item.getItemId()) {
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

    void setAllBtnState(final boolean isEnabled){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Button btn:mBtnList) {
                    btn.setEnabled(isEnabled);
                }
            }
        });
    }


    void setupInviteStartProvision() {
        Button btn = (Button) findViewById(R.id.btn_provision_invite_start_provisioning);
        mBtnList.add(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtnState(false);
                mNumElements = -1;
                mDeviceKey = null;
                PROV_INPUT_DATA provData = new PROV_INPUT_DATA();
                provData.netkey_index = mNetkeyIndex;
                provData.netkey = mNetworkKey;
                provData.address = mUnicastAddr;
                provData.flags = 0x00;
                provData.iv_index = mIVIndex;
                mProvState = ProvState.PROV_STATE_START;
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().inviteStartProvision(mUUID, provData);
                addLogMsg("invite_start_provisioning");
            }
        });
    }


    void setupAliInviteProvision() {
        Button btn = (Button) findViewById(R.id.btn_provision_ali_invite_provisioning);
        mBtnList.add(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllBtnState(false);
                mNumElements = -1;
                mDeviceKey = null;
                PROV_INPUT_DATA provData = new PROV_INPUT_DATA();
                provData.netkey_index = mNetkeyIndex;
                provData.netkey = mNetworkKey;
                provData.address = mUnicastAddr;
                provData.flags = 0x00;
                provData.iv_index = mIVIndex;
                mProvState = ProvState.PROV_STATE_START;
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().aliInviteProvision(mUUID, provData);
                addLogMsg("ali_invite_provisioning");
            }
        });
    }

    private void showConfigDialog(final Context context, String title, String message){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final Intent intent = new Intent(MeshDeviceProvisionActivity.this, MeshDeviceInfoActivity.class);

                intent.putExtra(MeshUtils.EXTRAS_BT_NAME, mBtDeviceName);
                intent.putExtra(MeshUtils.EXTRAS_BT_BD_ADDR, mBtDeviceBdAddr);
                intent.putExtra(MeshUtils.EXTRAS_DEVICE_UUID, mUUID);
                intent.putExtra(MeshUtils.EXTRAS_UNICAST_ADDR, mUnicastAddr);
                intent.putExtra(MeshUtils.EXTRAS_DEVICE_KEY, mDeviceKey);
                intent.putExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, mNetkeyIndex);
                startActivityForResult(intent, ENTER_CONFIG_PAGE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setAllBtnState(true);
            }
        });
        builder.create().show();
    }

    private void showAlertDialog(final Context context, String title, String message){
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


    }
