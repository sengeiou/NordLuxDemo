package com.airoha.btdlib.core;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by mtk60348 on 2018/6/15.
 */

class GattStateListenerMgr {
    private ConcurrentHashMap<String, GattStateListener> mListenerMap;

    public GattStateListenerMgr() {
        mListenerMap = new ConcurrentHashMap<>();
    }

    public synchronized void addListener(String name, GattStateListener listener) {
        if (name == null || listener == null) return;
        mListenerMap.put(name, listener);
    }

    public synchronized void removeListener(String name) {
        if (name == null) return;
        mListenerMap.remove(name);
    }

    public synchronized void onGattConnected(BluetoothGatt gatt) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onGattConnected(gatt);
        }
    }

    public synchronized void onGattDisconnected(BluetoothGatt gatt) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onGattDisconnected(gatt);
        }
    }

    public synchronized void onNewMtu(int mtu) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onNewMtu(mtu);
        }
    }

    public synchronized void onRequestMtuChangeStatus(boolean status) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onRequestMtuChangeStatus(status);
        }
    }

    public synchronized void onCharacteristicChanged(BluetoothGatt gatt,
                                                     BluetoothGattCharacteristic characteristic) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onCharacteristicChanged(gatt, characteristic);
        }
    }

    public synchronized void onServicesDiscovered(BluetoothGatt gatt, int status) {
        for (GattStateListener listener : mListenerMap.values()) {
            listener.onServicesDiscovered(gatt, status);
        }
    }
}
