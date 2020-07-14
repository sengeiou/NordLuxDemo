package com.airoha.btdlib.core;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Callbacks for the actions taken by AirohaLink
 *
 * @see AirohaLink
 */

public interface GattStateListener {
    /**
     * Indicating GATT Connected. SDK user can handle the followup by overriding this.
     */
    void onGattConnected(BluetoothGatt gatt);

    /**
     * Indicating for GATT Disconnected. SDK user can handle the followup by overriding this.
     */
    void onGattDisconnected(BluetoothGatt gatt);

    void onRequestMtuChangeStatus(boolean isAccepted);

    void onNewMtu(int mtu);

    void onServicesDiscovered(BluetoothGatt gatt, int status);

    void onCharacteristicChanged(BluetoothGatt gatt,
                                 BluetoothGattCharacteristic characteristic);
}
