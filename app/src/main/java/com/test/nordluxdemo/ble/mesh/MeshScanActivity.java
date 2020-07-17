package com.test.nordluxdemo.ble.mesh;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.airoha.libmesh.listener.MeshProvisionListener;
import com.airoha.libmeshparam.AirohaMeshUUID;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;
import com.test.nordluxdemo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class MeshScanActivity extends BaseActivity implements MeshProvisionListener {
    private ImageView imgClose;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_list_activity);
        imgClose=findViewById(R.id.imageView105);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mContext = this;

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "error_bluetooth_not_supported", Toast.LENGTH_SHORT).show();
        }

        mServiceIntent = new Intent(this, BluetoothLeService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mBluetoothLeScanner = mBluetoothAdapter.getBluetoothLeScanner();
        }

        mMeshDevicesListView = findViewById(R.id.mesh_list);


    }
    private final static String TAG = "Airoha_" + MeshScanActivity.class.getSimpleName();

    private MeshScanActivity mContext;
    protected BluetoothLeService mBluetoothLeService = null;
    protected ServiceConnection mServiceConnection = null;
    protected Intent mServiceIntent = null;

    private BluetoothLeScanner mBluetoothLeScanner;
    private ListView mMeshDevicesListView;

    private LeDeviceListAdapter mLeDeviceListAdapter;
    private BluetoothAdapter mBluetoothAdapter;
    private Handler mScanHandler;
    private boolean mScanning;
    private Timer mTimerForUpdate;
    private static int TIME_UPDATE = 1000;

    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    @Override
    public void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {
        mLeDeviceListAdapter.addDevice(device, rssi, uuid, oobInfo, uriHash);
        Log.d(TAG, device.getAddress() + ", UUID=" + MeshUtils.bytesToHexString(uuid) + ", oob info=" + MeshUtils.shortToHexString(oobInfo));
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
            Log.d(TAG, "onServiceConnected");
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) (service)).getService();
            mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().addListener(mContext.getLocalClassName(), mContext);
            scanLeDevice(true);
            mIsServiceConnected = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected");
            mServiceConnection = null;
            mBluetoothLeService = null;
            mIsServiceConnected = false;
            MeshScanActivity.this.finish();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan, menu);
        if (!mScanning) {
            menu.findItem(R.id.menu_stop).setVisible(false);
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_refresh).setActionView(null);
        } else {
            menu.findItem(R.id.menu_stop).setVisible(true);
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_refresh).setActionView(
                    R.layout.actionbar_indeterminate_progress);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                mLeDeviceListAdapter.clear();
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        mScanHandler = new Handler();
        // Initializes list view adapter.
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        mMeshDevicesListView.setAdapter(mLeDeviceListAdapter);


        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            mServiceConnection = new myServiceConnection();
            mIsServiceConnected = false;
            bindService(
                    mServiceIntent,
                    mServiceConnection,
                    Context.BIND_NOT_FOREGROUND);
        }
    }

    @Override
    protected void onPause() {
        scanLeDevice(false);
        mLeDeviceListAdapter.clear();

        if (mScanHandler != null) {
            mScanHandler.removeCallbacksAndMessages(null);
            mScanHandler = null;
        }
        if (mBluetoothLeService != null) {
            mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().removeListener(mContext.getLocalClassName());
            unbindService(mServiceConnection);
            mIsServiceConnected = false;
            mBluetoothLeService = null;
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        scanLeDevice(false);
        if (mBluetoothLeService != null) {
            mBluetoothLeService.setHandler(null);
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

    private void scanLeDevice(final boolean enable) {
        if (enable && !mScanning) {
            startBleScan();
            startUpdateTimer();

            if (mScanHandler != null) {
                mScanHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanLeDevice(false);
                    }
                }, SCAN_PERIOD);
            }
        } else if (!enable && mScanning) {
            stopBleScan();
            stopUpdateTimer();
        }
        invalidateOptionsMenu();
    }


    public void startUpdateTimer() {
        if (mTimerForUpdate != null) {
            mTimerForUpdate.cancel();
        }
        mTimerForUpdate = new Timer();
        mTimerForUpdate.schedule(new UpdateListTask(), TIME_UPDATE, TIME_UPDATE);
    }

    public void stopUpdateTimer() {
        if (mTimerForUpdate != null) {
            mTimerForUpdate.cancel();
        }
    }

    class UpdateListTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mLeDeviceListAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    // Adapter for holding devices found through scanning.
    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<MeshDevInfo> mMeshDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            super();
            mMeshDevices = new ArrayList<>();
            mInflator = MeshScanActivity.this.getLayoutInflater();
        }

        public void addDevice(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {
            String addr = device.getAddress();
            for (MeshDevInfo tmp : mMeshDevices) {
                if (tmp.mBtDevice.getAddress().equals(addr)) {
                    tmp.mRssi = rssi;
                    return;
                }
            }

            MeshDevInfo meshDevInfo = new MeshDevInfo();
            meshDevInfo.mBtDevice = device;
            meshDevInfo.mRssi = rssi;
            meshDevInfo.mUUID = uuid;
            meshDevInfo.mOobInfo = oobInfo;
            meshDevInfo.mUriHash = uriHash;

            mMeshDevices.add(meshDevInfo);
        }

        public BluetoothDevice getDevice(int position) {
            return mMeshDevices.get(position).mBtDevice;
        }

        public void clear() {
            mMeshDevices.clear();
        }

        @Override
        public int getCount() {
            return mMeshDevices.size();
        }

        @Override
        public Object getItem(int i) {
            return mMeshDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_mesh_updevice, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                viewHolder.deviceRssi = (TextView) view.findViewById(R.id.device_rssi);
                viewHolder.btnMeshProvision = (Button) view.findViewById(R.id.btnMeshProvision);

                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            MeshDevInfo meshDevInfo = mMeshDevices.get(i);

            final String deviceName = meshDevInfo.mBtDevice.getName();
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText(deviceName);
            } else {
                viewHolder.deviceName.setText("unknown_device");
            }


            viewHolder.deviceAddress.setText(meshDevInfo.mBtDevice.getAddress());
            viewHolder.deviceRssi.setText(String.valueOf(meshDevInfo.mRssi));

            viewHolder.btnMeshProvision.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout layout_0 = (RelativeLayout) v.getParent().getParent();
                    RelativeLayout layout_1 = (RelativeLayout) layout_0.findViewById(R.id.layoutDevInfo);
                    TextView tv_addr = (TextView) layout_1.findViewById(R.id.device_address);
                    String addr = tv_addr.getText().toString();

                    MeshDevInfo meshDevInfo = null;
                    for (MeshDevInfo tmp : mMeshDevices) {
                        if (tmp.mBtDevice.getAddress().equals(addr)) {
                            meshDevInfo = tmp;
                        }
                    }

                    final Intent intent = new Intent(MeshScanActivity.this, MeshDeviceProvisionActivity.class);

                    intent.putExtra(MeshUtils.EXTRAS_BT_NAME, meshDevInfo.mBtDevice.getName());
                    intent.putExtra(MeshUtils.EXTRAS_BT_BD_ADDR, meshDevInfo.mBtDevice.getAddress());
                    intent.putExtra(MeshUtils.EXTRAS_DEVICE_UUID, meshDevInfo.mUUID);

                    startActivity(intent);
                }
            });

            return view;
        }

        public class MeshDevInfo {
            BluetoothDevice mBtDevice;
            int mRssi;
            byte[] mUUID;
            short mOobInfo;
            byte[] mUriHash;
        }
    }

    public void startBleScan() {
        mScanning = true;

        UUID[] uuids_filter = new UUID[1];
        uuids_filter[0] = UUID.fromString(AirohaMeshUUID.MESH_PROVISION_SERVICE_UUID);
        mBluetoothAdapter.startLeScan(uuids_filter, mLeScanCallback);

        Log.d(TAG, "Bluetooth is currently scanning...");
    }

    public void stopBleScan() {
        mScanning = false;
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        } else {
            mBluetoothLeScanner.stopScan(mScanCallback);
        }

        Log.d(TAG, "Scanning has been stopped");
    }

    // Device scan callback.
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
            Log.d(TAG, result.toString());
            List<ParcelUuid> uuids = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                uuids = result.getScanRecord().getServiceUuids();
            }
            if (uuids != null && mBluetoothLeService != null) {
                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().parseMeshScanResult(result);
            }
        }
    };

    static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView deviceRssi;
        Button btnMeshProvision;
    }
}
