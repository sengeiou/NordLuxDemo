package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshVendorModelListener;
import com.airoha.libmeshparam.model.ble_mesh_access_message_rx_t;
import com.airoha.libnativemesh.AirohaMesh;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Vendor Model.
 */
public class MeshVendorModel {
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshVendorModelListenerMgr mMeshVendorModelListenerMgr = null;

    MeshVendorModel(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshVendorModelListenerMgr = new MeshVendorModelListenerMgr();
    }

    public void addListener(String name, MeshVendorModelListener listener) {
        mMeshVendorModelListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshVendorModelListenerMgr.removeListener(name);
    }

    /**
     * Add Vendor Model <p>
     * @param element_index is the index of selected element.
     * @param company_id is the company id.
     * @param opCode is opCode of vendor model.
     * @return the execution status
     */
    public short addVendorModel(short element_index, short company_id, byte[] opCode){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().addVendorModel(element_index, company_id, opCode);
        }
        return ret;
    }

    /**
     * Send Vendor Model Msg<p>
     * The result is reported asynchronously through {@link MeshVendorModelListener#OnMeshVendorModelMsgReceived(ble_mesh_access_message_rx_t msg)}
     * @param company_id is the company id.
     * @param op_code is the op code.
     * @param dst_addr is the destination address.
     * @param data is the message to be sent.
     * @param data_len is the length of the message to be sent.
     */
    public void sendVendorModelMsg(short company_id, byte op_code, short dst_addr, byte[] data, short data_len){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            AirohaMesh.getInstance().sendVendorMsg(company_id, op_code, dst_addr, data, data_len);
        }
    }
}
