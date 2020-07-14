package com.airoha.libmesh.listener;

import android.bluetooth.BluetoothGatt;

public interface MeshServiceListener {

    /**
     * Callback for {@link com.airoha.libmesh.core.AirohaMeshMgr#onServicesDiscovered(BluetoothGatt, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     */
    void onMeshProvisionServiceFound();

    /**
     * Callback for {@link com.airoha.libmesh.core.AirohaMeshMgr#onServicesDiscovered(BluetoothGatt, int)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     */
    void onMeshProxyServiceFound();
}
