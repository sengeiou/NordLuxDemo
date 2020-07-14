package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshLightingModelListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_last_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_linear_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_lightness_client_evt_status_t;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Light Lightness Model.
 */

public class MeshLightLightnessModel{
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshLightingModelListenerMgr mMeshLightingModelListenerMgr = null;

    MeshLightLightnessModel(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshLightingModelListenerMgr = new MeshLightingModelListenerMgr();
    }

    public void addListener(String name, MeshLightingModelListener listener) {
        mMeshLightingModelListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshLightingModelListenerMgr.removeListener(name);
    }
    /**
     * Add a Light Lightness client model.
     *
     * @param elementIndex is the specified element for adding the Light Lightness client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addLightClientLightness(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addLightLightnessClient(elementIndex);
        }
        return ret;
    }

    /**
     * Sends a request to get the Light Lightness Actual state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightness(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightness(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light Lightness Actual state of an element.
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightnessStatusReceived(ble_mesh_lightness_client_evt_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param lightness the target value of the Light Lightness Actual state.
     * @param tid       transaction Identifier.
     * @param transTime transition time.
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightness(short dstAddr, byte ttl, short lightness, byte tid,
                                byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightness(dstAddr, ttl, lightness, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light Lightness Linear state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLinearLightness(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLinearLightness(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light Lightness Linear state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLinearLightnessStatusReceived(ble_mesh_lightness_client_evt_linear_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param lightness the target value of the Light Lightness Actual state.
     * @param tid       transaction Identifier.
     * @param transTime transition time.
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLinearLightness(short dstAddr, byte ttl, short lightness, byte tid,
                            byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLinearLightness(dstAddr, ttl, lightness, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light Lightness Last state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLastLightnessStatusReceived(ble_mesh_lightness_client_evt_last_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLastLightness(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLastLightness(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light Lightness Defalut state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getDefaultLightness(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getDefaultLightness(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light Lightness Defalut state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshDefaultLightnessStatusReceived(ble_mesh_lightness_client_evt_default_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @param lightness the target value of the Light Lightness Actual state.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setDefaultLightness(short dstAddr, byte ttl, short lightness, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setDefaultLightness(dstAddr, ttl, lightness, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light Lightness Range state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightnessRange(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getRangeLightness(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }


    /**
     * Sends a request to set the Light Lightness Range state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshRangeLightnessStatusReceived(ble_mesh_lightness_client_evt_range_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param rangeMin  the range min value.
     * @param rangeMax the range max value.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightnessRange(short dstAddr, byte ttl, short rangeMin, short rangeMax, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setRangeLightness(dstAddr, ttl, rangeMin, rangeMax, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }
}
