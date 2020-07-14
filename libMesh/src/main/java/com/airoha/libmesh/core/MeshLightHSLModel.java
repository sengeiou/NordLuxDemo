package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshLightingModelListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_default_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_hue_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_range_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_saturation_status_t;
import com.airoha.libmeshparam.model.lighting.ble_mesh_hsl_client_evt_status_t;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Light HSL Model.
 */

public class MeshLightHSLModel{
    AirohaMeshMgr mAirohaMeshMgr = null;

    MeshLightingModelListenerMgr mMeshLightingModelListenerMgr = null;

    MeshLightHSLModel(AirohaMeshMgr mgr) {
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
     * Add a Light HSL client model.
     *
     * @param elementIndex is the specified element for adding the Light HSL client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addLightClientHSL(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addLightHslClient(elementIndex);
        }
        return ret;
    }

    /**
     * Sends a request to get the Light HSL Lightness, Light HSL Hue, and Light HSL Saturation states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightHSL(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightHsl(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light HSL Lightness state, Light HSL Hue state, and the Light HSL Saturation state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl               the received TTL value.
     * @param lightness the target value of the Light HSL Lightness state.
     * @param hue           the target value of the Light HSL Hue state.
     * @param saturation the target value of the Light HSL Saturation state.
     * @param tid            transaction Identifier.
     * @param transTime transition time.
     * @param delay         delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param acknowledged  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightHSL(short dstAddr, byte ttl, short lightness, short hue, short saturation, byte tid,
                               byte transTime, byte delay, boolean acknowledged) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightHsl(dstAddr, ttl, lightness, hue, saturation,
                    tid, transTime, delay, acknowledged);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Target Light HSL states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHslStatusReceived(ble_mesh_hsl_client_evt_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightTargetHsl(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightTargetHsl(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light HSL Default states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightDefaultHsl(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightDefaultHsl(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light HSL Default state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightDefaultHslStatusReceived(ble_mesh_hsl_client_evt_default_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl               the received TTL value.
     * @param lightness the target value of the Light HSL Lightness state.
     * @param hue           the target value of the Light HSL Hue state.
     * @param saturation the target value of the Light HSL Saturation state.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightDefaultHsl(short dstAddr, byte ttl,
                                  short lightness, short hue, short saturation,
                                  boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightDefaultHsl(dstAddr, ttl,
                    lightness, hue, saturation, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light HSL Range states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightHslRange(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightHslRange(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light HSL Range state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHslRangeStatusReceived(ble_mesh_hsl_client_evt_range_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl               the received TTL value.
     * @param hue_min   the min value of the Light HSL Hue state.
     * @param hue_max           the max value of the Light HSL Hue state.
     * @param saturation_min the min value of the Light HSL Saturation state.
     * @param saturation_max the max value of the Light HSL Saturation state.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightHslRange(short dstAddr, byte ttl,
                                short hue_min, short hue_max,
                                short saturation_min, short saturation_max,
                                boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightHslRange(dstAddr, ttl,
                    hue_min, hue_max, saturation_min, saturation_max, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light HSL Hue states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightHue(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightHue(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light HSL Hue state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightHueStatusReceived(ble_mesh_hsl_client_evt_hue_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl               the received TTL value.
     * @param hue           the target value of the Light HSL Hue state.
     * @param tid            transaction Identifier.
     * @param transTime transition time.
     * @param delay         delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightHue(short dstAddr, byte ttl,
                           short hue, byte tid,
                           byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightHue(dstAddr, ttl,
                    hue, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Light HSL Saturation states of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLightSaturation(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLightSaturation(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the Light HSL Saturation state of an element.<p>
     * The result is reported asynchronously through {@link MeshLightingModelListener#onMeshLightSaturationStatusReceived(ble_mesh_hsl_client_evt_saturation_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl               the received TTL value.
     * @param saturation the target value of the Light HSL Saturation state.
     * @param tid            transaction Identifier.
     * @param transTime transition time.
     * @param delay         delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLightSaturation(short dstAddr, byte ttl,
                                  short saturation, byte tid,
                                  byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLightSaturation(dstAddr, ttl,
                    saturation, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }
}
