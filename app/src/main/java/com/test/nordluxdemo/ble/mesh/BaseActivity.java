package com.test.nordluxdemo.ble.mesh;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.airoha.btdlib.core.GattStateListener;
import com.airoha.libmesh.listener.MeshParamUpdateListener;
import com.airoha.libmesh.listener.MeshServiceListener;

/**
 * Created by MTK60348 on 2018/6/12.
 */

public class BaseActivity extends AppCompatActivity implements GattStateListener, MeshServiceListener, MeshParamUpdateListener {

    static int DELAY_MS_FOR_CONNECT = 1000;
    public boolean mIsServiceConnected = false;
    @Override
    public void onGattConnected(BluetoothGatt gatt) {

    }

    @Override
    public void onGattDisconnected(BluetoothGatt gatt) {

    }

    @Override
    public void onRequestMtuChangeStatus(boolean isAccepted) {

    }

    @Override
    public void onNewMtu(int mtu) {
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {

    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {

    }

    @Override
    public void onMeshProvisionServiceFound() {

    }

    @Override
    public void onMeshProxyServiceFound() {

    }

    @Override
    public void onMeshIvUpdated(int iv_index, byte state) {
        MeshUtils.gIvIndex = iv_index;
        PreferenceUtility.saveMeshIvIndex(getApplicationContext(), iv_index);
    }

    @Override
    public void onBackPressed() {
        if (!mIsServiceConnected) {
            Toast.makeText(this, "Please wait for service connected.", Toast.LENGTH_LONG).show();
            return;
        }
        super.onBackPressed();
    }
}
