package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libnativemesh.AirohaMesh;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to handle Mesh Core.
 */

public class MeshCore {
    AirohaMeshMgr mAirohaMeshMgr = null;

    MeshCore(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
    }
    /**
     * This function is used for mesh module initialization.
     *
     * @param devUUID the device UUID.
     * @return the execution status {@link com.airoha.libmeshparam.ENUM_DEF.CMD_EVENT_ID}
     */
    public int init(final String fileName, final byte[] devUUID) {
        int ret = 0;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
//            Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
            AirohaMesh.getInstance().setListener(mAirohaMeshMgr);
            AirohaMesh.getInstance().create();

            String dirPath = mAirohaMeshMgr.mAirohaLink.getContext().getFilesDir().getAbsolutePath() + "/";

            mAirohaMeshMgr.mIsMeshInitDone = false;
            ret = AirohaMesh.getInstance().init(dirPath, fileName, devUUID);
            ret = (ret == 0 && mAirohaMeshMgr.mIsMeshInitDone) ? 0: -1;
        }
        return ret;
    }

    /**
     * This function is used for bearer module destroying
     *
     * @return the execution status {@link com.airoha.libmeshparam.ENUM_DEF.CMD_EVENT_ID}
     */
    public int destroy() {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().destroy();
            if (ret == 0) {
                mAirohaMeshMgr.mIsMeshInitDone = false;
            }
        }
        return ret;
    }

    public void updateServiceState(boolean isConnected) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            AirohaMesh.getInstance().updateServiceState(isConnected);
        }
    }

    public void updateMTU(int mtu) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            AirohaMesh.getInstance().updateMTU(mtu);
        }
    }

//    public void updateMeshCore() {
//        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
//        if (!mAirohaMeshMgr.mIsMeshInitDone) {
//            Log.e(TAG, NOT_INIT_ERROR);
//        } else {
//            AirohaMesh.getInstance().updateMeshCore();
//        }
//    }

    public boolean gatttDataIn(final byte[] gattData) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        boolean ret = false;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else if (gattData != null && gattData.length > 0) {
            AirohaMesh.getInstance().putGattDataIn(gattData);
            ret = true;
        }
        return ret;
    }
}
