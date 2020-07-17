package com.test.nordluxdemo.ble.mesh;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.test.nordluxdemo.R;

public class MeshDeviceInfoActivity extends AppCompatActivity {

    private final static String TAG = "Airoha_" + MeshDeviceInfoActivity.class.getSimpleName();

    private BluetoothAdapter mBluetoothAdapter;
    Button mBtnConfig;


    String mBtDeviceName;
    String mBtDeviceBdAddr;
    byte[] mUUID;
    short mUnicastAddr;
    byte[] mDeviceKey;
    short mNetkeyIndex;

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_ACTIVITY = 5566;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mesh_device_info_activity);

        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show();
        }

        final Intent intent = getIntent();
        mBtDeviceName = intent.getStringExtra(MeshUtils.EXTRAS_BT_NAME);
        mBtDeviceBdAddr = intent.getStringExtra(MeshUtils.EXTRAS_BT_BD_ADDR);
        mUUID = intent.getByteArrayExtra(MeshUtils.EXTRAS_DEVICE_UUID);
        mUnicastAddr = intent.getShortExtra(MeshUtils.EXTRAS_UNICAST_ADDR, (short)-1);
        mDeviceKey = intent.getByteArrayExtra(MeshUtils.EXTRAS_DEVICE_KEY);
        mNetkeyIndex = intent.getShortExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, MeshUtils.NET_KEY_INDEX);

        String networkName = MeshUtils.NET_NAME;
        byte[] netKey = MeshUtils.NET_KEY;
        MeshUtils.NETKEY_INFO mNetkeyInfo = MeshUtils.getNetkeyInfo(mNetkeyIndex);
        if (mNetkeyInfo != null) {
            networkName = mNetkeyInfo.mNetworkName;
            netKey = mNetkeyInfo.mNetKey;
        }

        setTextView(R.id.textView_devInfo_devName, mBtDeviceName == null ? "Unknown Device":mBtDeviceName);
        setTextView(R.id.textView_devInfo_uuid, MeshUtils.bytesToHexString(mUUID));
        setTextView(R.id.textView_devInfo_network, networkName);
        setTextView(R.id.textView_devInfo_net_key, MeshUtils.bytesToHexString(netKey));
        setTextView(R.id.textView_devInfo_app_key, MeshUtils.bytesToHexString(MeshUtils.APP_KEY));
        if (mUnicastAddr >= 0) {
            setTextView(R.id.textView_devInfo_unicatAddr, MeshUtils.shortToHexString(mUnicastAddr));
        }
        if (mDeviceKey != null) {
            setTextView(R.id.textView_devInfo_dev_key, MeshUtils.bytesToHexString(mDeviceKey));
        }

        mBtnConfig = (Button) findViewById(R.id.btn_devInfo_config);


        mBtnConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(MeshDeviceInfoActivity.this, MeshDeviceConfigActivity.class);

                intent.putExtra(MeshUtils.EXTRAS_BT_NAME, mBtDeviceName);
                intent.putExtra(MeshUtils.EXTRAS_BT_BD_ADDR, mBtDeviceBdAddr);
                intent.putExtra(MeshUtils.EXTRAS_UNICAST_ADDR, mUnicastAddr);
                intent.putExtra(MeshUtils.EXTRAS_DEVICE_KEY, mDeviceKey);
                intent.putExtra(MeshUtils.EXTRAS_NETWORK_KEYIDX, mNetkeyIndex);

                startActivityForResult(intent, REQUEST_ACTIVITY);
            }
        });


    }

    void setTextView(final int resId, final String text) {
        if (text == null) {
            return;
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = (TextView) findViewById(resId);

                if (tv == null){
                    return;
                }

                tv.setText(text);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
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
            MeshDeviceInfoActivity.this.finish();
            return;
        } else if (requestCode == REQUEST_ACTIVITY && resultCode == MeshUtils.RESET_NODE_RESULT_CODE) {
            MeshDeviceInfoActivity.this.finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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