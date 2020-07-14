package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshLightingModelListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_temperature_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_ctl_client_evt_temperature_status_t;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Light CTL Model.
 */

public class MeshLightCTLModel{
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshLightingModelListenerMgr mMeshLightingModelListenerMgr = null;

    MeshLightCTLModel(AirohaMeshMgr mgr) {
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
     * Add a Light CTL client model.
     *
     * @param elementIndex is the specified element for adding the Light CTL client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addLightClientCTL(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addLightCtlClient(elementIndex);
        }
        return ret;
    }

    /**
     * Sends a request to get the Light CTL state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightCTL(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightCtl(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light CTL Lightness state, Light CTL Temperature state, and the Light CTL Delta UV state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlStatusReceived(ble_mesh_ctl_client_evt_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param lightness the target value of the Light CTL Lightness state.
     * @param temperature the target value of the Light CTL Temperature state.
     * @param deltaUV the target value of the Light CTL Delta UV state.
     * @param tid       transaction Identifier.
     * @param transTime transition time.
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param acknowledged  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightCTL(short dstAddr, byte ttl, short lightness, short temperature, short deltaUV,
                               byte tid, byte transTime, byte delay, boolean acknowledged) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightCtl(dstAddr, ttl, lightness, temperature, deltaUV,
                    tid, transTime, delay, acknowledged);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light CTL Temperature state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightCtlTemperature(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightCtlTemperature(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light CTL Light CTL Temperature state, and the Light CTL Delta UV state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlTemperatureStatusReceived(ble_mesh_ctl_client_evt_temperature_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param temperature the target value of the Light CTL Temperature state.
     * @param deltaUV the target value of the Light CTL Delta UV state.
     * @param tid       transaction Identifier.
     * @param transTime transition time.
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightCtlTemperature(short dstAddr, byte ttl,
                                      short temperature, short deltaUV,
                                      byte tid, byte transTime, byte delay,
                                      boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightCtlTemperature(dstAddr, ttl, temperature, deltaUV,
                    tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light CTL Temperature Range state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightCtlTemperatureRange(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightCtlTemperatureRange(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light CTL Light CTL Temperature Range state.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlTemperatureRangeStatusReceived(ble_mesh_ctl_client_evt_temperature_range_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param min the min value of the Light CTL Temperature range.
     * @param max the max value of the Light CTL Temperature range.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightCtlTemperatureRange(short dstAddr, byte ttl, short min, short max,
                                           boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightCtlTemperatureRange(dstAddr, ttl, min, max, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light CTL Default state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightDefaultCtl(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightDefaultCtl(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light CTL Light CTL Default state.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightCtlDefaultStatusReceived(ble_mesh_ctl_client_evt_default_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param lightness the target value of the Light CTL Lightness state.
     * @param temperature the target value of the Light CTL Temperature state.
     * @param deltaUV the target value of the Light CTL Delta UV state.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightDefaultCtl(short dstAddr, byte ttl,
                                  short lightness, short temperature, short deltaUV,
                                  boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightDefaultCtl(dstAddr, ttl, lightness,
                    temperature, deltaUV, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }
}
