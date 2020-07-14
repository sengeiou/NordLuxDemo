package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Core.
 */

public class MeshConfig{

    AirohaMeshMgr mAirohaMeshMgr = null;

    MeshConfig(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
    }
    /**
     * Set unicast address for the primary element of this node
     *
     * @param addr the address assigned to this phone
     */
    public int setProvisionerAddr(short addr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().setProvisionerAddr(addr);
        }
        return ret;
    }

    /**
     * Add the network key.
     *
     * @param netKey      is the 16-byte network key.
     * @param netKeyIndex means the assigned key index for this key.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int addNetKey(byte[] netKey, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addNetKey(netKey, netKeyIndex);
        }
        return ret;
    }

    /**
     * Add the application key.
     *
     * @param netKeyIndex contains a 16-byte application key.
     * @param appKey      is the application key index.
     * @param appKeyIndex is the network key index bound with this application key.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int addAppKey(short netKeyIndex, byte[] appKey, short appKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addAppKey(netKeyIndex, appKey, appKeyIndex);
        }
        return ret;
    }

    /**
     * Add the device key.
     *
     * @param devKey  is the 16-byte device key.
     * @param uniAddr means unicast address associated with this device key.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int addDevKey(byte[] devKey, short uniAddr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addDevKey(devKey, uniAddr);
        }
        return ret;
    }

    /**
     * Bind the model to assigned application key. <p>
     *
     * @param modelHandle is the handle of model to be bound with application key.
     * @param appKeyIndex is the application key index to be bound with the model.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int bindModelApp(short modelHandle, short appKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().bindModelApp(modelHandle, appKeyIndex);
        }
        return ret;
    }


    /**
     * Set the IV index and IV update state.
     *
     * @param index is the current IV index.
     * @param state is the current state of the IV update.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int setIvIndex(int index, byte state){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().setIvIndex(index, state);
        }
        return ret;
    }

    /**
     * Start or stop IV index update process by specified network key.
     *
     * @param index means which key used to start this process.
     * @param on indicates to start of stop IV index update process.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int updateIvIndex(short index, boolean on){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().updateIvIndex(index, on);
        }
        return ret;
    }

    /**
     * Enable or disable IV update test mode.
     *
     * @param on indicates to enable or disable test mode.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int setTestMode(boolean on) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().setTestMode(on);
        }
        return ret;
    }
}
