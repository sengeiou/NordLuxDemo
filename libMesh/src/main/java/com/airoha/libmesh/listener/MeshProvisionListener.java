package com.airoha.libmesh.listener;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;

import com.airoha.libmeshparam.PROV_INPUT_DATA;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;

public interface MeshProvisionListener {
    /**
     * Callback for {@link com.airoha.btdlib.core.MeshProvision#parseScanResult(ScanResult)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param device  The BluetoothDevice object reported by LE scan.
     * @param rssi    The RSSI value reported by LE scan.
     * @param uuid    The unprovisioned device UUID.
     * @param oobInfo The OOB information of the unprovisioned device.
     * @param uriHash The Uri hash value of the unprovisioned device; Can be NULL.
     */
    void onMeshUdFound(BluetoothDevice device, int rssi, byte[] uuid, short oobInfo, byte[] uriHash);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshProvision#inviteStartProvision(byte[], PROV_INPUT_DATA)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param provCap  The provision capabilities of target device.
     */
    void onMeshProvCapReceived(ble_mesh_prov_capabilities_t provCap);

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshProvision#inviteStartProvision(byte[], PROV_INPUT_DATA)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     * @param state  The result of provisioning.
     * @param deviceKey    The device key of target device.
     * @param address    The unicast address of target device.
     */
    void onMeshProvStateChanged(boolean state, byte[] deviceKey, short address);

    void onMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response provAliResp);
    void onMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device provAliConfirmDevice);
}
