package com.test.nordluxdemo.ble.mesh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.airoha.libmesh.listener.MeshProvisionListener;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.test.nordluxdemo.R;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class MeshDeviceProvisionActivity extends AppCompatActivity implements MeshProvisionListener {
    private final static String TAG = MeshDeviceProvisionActivity.class.getSimpleName();
    private static final int ENTER_CONFIG_PAGE = 2;
    protected BluetoothLeService mBluetoothLeService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mesh_device_provision);
        final Intent intent=getIntent();
        String mBtDeviceName = intent.getStringExtra(MeshUtils.EXTRAS_BT_NAME);
        String mBtDeviceBdAddr = intent.getStringExtra(MeshUtils.EXTRAS_BT_BD_ADDR);
        byte[] mUUID = intent.getByteArrayExtra(MeshUtils.EXTRAS_DEVICE_UUID);
        Log.i("TTT",String.valueOf(mUUID));
        //Log.i("TTT",mBtDeviceName);
        //Log.i("TTT", String.valueOf(mUUID));

    }

    @Override
    public void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash) {

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
                    //setAllBtnState(true);
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
    public void onMeshAliProvisioningConfirmationDevice(final com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice) {
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
    public void onMeshAliProvisioningResponse(final com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response provAliResp) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Type listType = new TypeToken<ble_mesh_evt_prov_ali_response>(){}.getType();
                Gson gson = new Gson();
                addLogMsg("onMeshAliProvisioningResponse: " + gson.toJson(provAliResp, listType));

                // need to use three tuples to retrive the static OOB authentication value, here is an example with no OOB.
                byte[] text = new byte[32];
                Arrays.fill(text, (byte)0x00);
                System.arraycopy(provAliResp.provisioner_random, 0, text, 0, 16);

                byte[] out = MeshCrypto.aesCmac(provAliResp.confirmation_key, text);

                mBluetoothLeService.getAirohaMeshMgr().getMeshProvision().aliProvConfirmation(out);
            }
        });
    }

    @Override
    public void onMeshProvCapReceived(final com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t provCap) {
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
    private enum ProvState {
        PROV_STATE_IDLE,
        PROV_STATE_START,
        PROV_STATE_DONE
    };
    void addLogMsg(String msg) {
        Log.d(TAG, msg);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS    ");
        String timeStr = sdf.format(new Date());
        MeshUtils.gLogAdapter.add(timeStr + msg);
    }

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
                //setAllBtnState(true);
            }
        });
        builder.create().show();
    }
}