package com.airoha.libmesh.core;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.ScanResult;
import android.os.ParcelUuid;
import android.util.Log;

import com.airoha.libmesh.listener.MeshProvisionListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.PROV_INPUT_DATA;
import com.airoha.libmeshparam.SCAN_UD_RESULT;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_confirmation_device;
import com.airoha.libmeshparam.prov.ble_mesh_evt_prov_ali_response;
import com.airoha.libmeshparam.prov.ble_mesh_prov_capabilities_t;
import com.airoha.libnativemesh.MeshUtils;

import java.util.List;
import java.util.UUID;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;
import static com.airoha.libmeshparam.AirohaMeshUUID.MESH_PROVISION_SERVICE_UUID;
import static com.airoha.libnativemesh.MeshUtils.MESH_UUID_LENGTH;

/**
 * Provide API to provision Mesh device.
 */

public class MeshProvision{
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshProvisionListenerMgr mMeshProvisionListenerMgr = null;

    MeshProvision(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshProvisionListenerMgr = new MeshProvisionListenerMgr();
    }

    public void addListener(String name, MeshProvisionListener listener) {
        mMeshProvisionListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshProvisionListenerMgr.removeListener(name);
    }

    /**
     * Parse the ADV to find Un-Provisioned device.<p>
     * The Un-Provisioned device information is reported asynchronously through  {@link MeshProvisionListener#onMeshUdFound(BluetoothDevice, int, byte[], short, byte[])}.
     *
     * @param btDev      is aBluetoothDevice object reported by LE scan.
     * @param rssi       is the RSSI value reported by LE scan.
     * @param scanRecord is the scanRecord reported by LE scan.
     */
    public void parseMeshADV(BluetoothDevice btDev, int rssi, byte[] scanRecord) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            SCAN_UD_RESULT tmp = parseADV(scanRecord);
            if (tmp != null) {
                mMeshProvisionListenerMgr.onMeshUdFound(btDev, rssi, tmp.uuid, tmp.oob_info, tmp.uri_hash);
            }
        }
    }

    public SCAN_UD_RESULT parseADV(final byte[] scanRecord) {
        if(scanRecord.length == 0)
        {
            return null;
        }
        SCAN_UD_RESULT ret = null;

        ScanRecord result = ScanRecord.parseFromBytes(scanRecord);

        List<ParcelUuid> uuids = result.getServiceUuids();
        if (uuids != null) {
            ParcelUuid targetUuid = new ParcelUuid(UUID.fromString(MESH_PROVISION_SERVICE_UUID));
            if (uuids.contains(targetUuid)) {
                ret = new SCAN_UD_RESULT();
                byte[] servicedata = result.getServiceData(targetUuid);
                final byte[] uuid = new byte[MESH_UUID_LENGTH];
                System.arraycopy(servicedata, 0, uuid, 0, MESH_UUID_LENGTH);
                short oobinfo = (short) MeshUtils.setByte((byte) 0, (byte) 0, servicedata[16], servicedata[17]);
                ret.uuid = uuid;
                ret.oob_info = oobinfo;
                ret.uri_hash = null;
            }
        }
        return ret;
    }

    /**
     * Parse the ScanResult  to find Un-Provisioned device.<p>
     * The Un-Provisioned device information is reported asynchronously through  {@link MeshProvisionListener#onMeshUdFound(BluetoothDevice, int, byte[], short, byte[])}.
     *
     * @param result is the ScanResult reported by LE scan.
     */
    public void parseMeshScanResult(ScanResult result) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            SCAN_UD_RESULT tmp = parseScanResult(result);
            if (tmp != null) {
                mMeshProvisionListenerMgr.onMeshUdFound(result.getDevice(), result.getRssi(), tmp.uuid, tmp.oob_info, tmp.uri_hash);
            }
        }
    }

    public SCAN_UD_RESULT parseScanResult(final ScanResult result) {
        if (result == null) {
            return null;
        }

        SCAN_UD_RESULT ret = null;

        List<ParcelUuid> uuids = result.getScanRecord().getServiceUuids();
        if (uuids != null) {
            ParcelUuid targetUuid = new ParcelUuid(UUID.fromString(MESH_PROVISION_SERVICE_UUID));
            if (uuids.contains(targetUuid)) {
                ret = new SCAN_UD_RESULT();
                byte[] servicedata = result.getScanRecord().getServiceData(targetUuid);
                final byte[] uuid = new byte[MESH_UUID_LENGTH];
                System.arraycopy(servicedata, 0, uuid, 0, MESH_UUID_LENGTH);
                short oobinfo = (short) MeshUtils.setByte((byte) 0, (byte) 0, servicedata[16], servicedata[17]);
                ret.uuid = uuid;
                ret.oob_info = oobinfo;
                ret.uri_hash = null;
            }
        }

        return ret;
    }

    /**
     * Invite the provisioning process for a specific UUID and then start provisioning when receives the ble_mesh_prov_capabilities_t. <p>
     * The provision capabilities is reported asynchronously through {@link  MeshProvisionListener#onMeshProvCapReceived(ble_mesh_prov_capabilities_t)} <p>
     * The provision result is reported asynchronously through {@link  MeshProvisionListener#onMeshProvStateChanged(boolean, byte[], short)}
     *
     * @param meshUUID is the 128-bit UUID of the target device.
     * @param provData is the provisioning invite parameters.
     * @return true if provision successfully, otherwise false.
     */
    public boolean inviteStartProvision(byte[] meshUUID, PROV_INPUT_DATA provData) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());

        mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONING;
        mAirohaMeshMgr.mProvData = new PROV_INPUT_DATA();
        mAirohaMeshMgr.mProvData.netkey = provData.netkey;
        mAirohaMeshMgr.mProvData.address = provData.address;
        mAirohaMeshMgr.mProvData.flags = provData.flags;
        mAirohaMeshMgr.mProvData.iv_index = provData.iv_index;
        mAirohaMeshMgr.mProvData.netkey_index = provData.netkey_index;
        AirohaMesh.getInstance().inviteProvision(meshUUID, (byte)0x00);

        return true;
    }

    /**
     * (For Ali)Invite the provisioning process for a specific UUID and then start provisioning when receives the ble_mesh_prov_capabilities_t. <p>
     * The provision capabilities is reported asynchronously through {@link  MeshProvisionListener#onMeshProvCapReceived(ble_mesh_prov_capabilities_t)} <p>
     * The provision response is reported asynchronously through {@link  MeshProvisionListener#onMeshAliProvisioningResponse(ble_mesh_evt_prov_ali_response)}  <p>
     * The provision confirmation is reported asynchronously through {@link  MeshProvisionListener#onMeshAliProvisioningConfirmationDevice(ble_mesh_evt_prov_ali_confirmation_device)}  <p>
     * The provision result is reported asynchronously through {@link  MeshProvisionListener#onMeshProvStateChanged(boolean, byte[], short)}
     *
     * @param meshUUID is the 128-bit UUID of the target device.
     * @param provData is the provisioning invite parameters.
     * @return true if provision successfully, otherwise false.
     */
    public boolean aliInviteProvision(byte[] meshUUID, PROV_INPUT_DATA provData) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());

        mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONING;
        mAirohaMeshMgr.mProvData = new PROV_INPUT_DATA();
        mAirohaMeshMgr.mProvData.netkey = provData.netkey;
        mAirohaMeshMgr.mProvData.address = provData.address;
        mAirohaMeshMgr.mProvData.flags = provData.flags;
        mAirohaMeshMgr.mProvData.iv_index = provData.iv_index;
        mAirohaMeshMgr.mProvData.netkey_index = provData.netkey_index;
        AirohaMesh.getInstance().aliInviteProvision(meshUUID, (byte)0x00);

        return true;
    }

    public void aliProvConfirmation(byte[] out) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());

        mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONING;
        AirohaMesh.getInstance().aliProvConfirmation(out);
    }

    public void aliProvProvideAuthenticationResult(boolean result) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());

        mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_PROVISIONING;
        AirohaMesh.getInstance().aliProvProvideAuthenticationResult(result);
    }
}
