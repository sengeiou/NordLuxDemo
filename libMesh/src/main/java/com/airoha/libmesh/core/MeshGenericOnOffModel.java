package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshGenericOnOffModelListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_battery_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_default_transition_time_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_global_location_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_level_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_local_location_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_on_power_up_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_onoff_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_default_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_last_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_level_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_power_range_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_properties_status_t;
import com.airoha.libmeshparam.model.generic.ble_mesh_generic_client_evt_property_status_t;

import java.util.Arrays;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh GenericOnOff Model
 */

public class MeshGenericOnOffModel{
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshGenericOnOffModelListenerMgr mMeshGenericOnOffModelListenerMgr = null;

    MeshGenericOnOffModel(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshGenericOnOffModelListenerMgr = new MeshGenericOnOffModelListenerMgr();
    }

    public void addListener(String name, MeshGenericOnOffModelListener listener) {
        mMeshGenericOnOffModelListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshGenericOnOffModelListenerMgr.removeListener(name);
    }

    /**
     * Add a GenericOnOff client model.
     *
     * @param elementIndex is the specified element for adding the GenericOnOff client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericOnOffClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericOnOffClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericLevel client model.
     *
     * @param elementIndex is the specified element for adding the GenericLevel client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericLevelClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericLevelClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericDefaultTransitionTime client model.
     *
     * @param elementIndex is the specified element for adding the GenericDefaultTransitionTime client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericDefaultTransitionTimeClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericDefaultTransitionTimeClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericPowerOnOff client model.
     *
     * @param elementIndex is the specified element for adding the GenericPowerOnOff client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericPowerOnOffClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericPowerOnOffClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericPowerLevel client model.
     *
     * @param elementIndex is the specified element for adding the GenericPowerLevel client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericPowerLevelClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericPowerLevelClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericBattery client model.
     *
     * @param elementIndex is the specified element for adding the GenericBattery client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericBatteryClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericBatteryClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericLocation client model.
     *
     * @param elementIndex is the specified element for adding the GenericLocation client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericLocationClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericLocationClient(elementIndex);
        }
        return ret;
    }

    /**
     * Add a GenericProperty client model.
     *
     * @param elementIndex is the specified element for adding the GenericProperty client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addGenericPropertyClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addGenericPropertyClient(elementIndex);
        }
        return ret;
    }

    /**
     * Sends a request to get Generic OnOff state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getGenericOnOff(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getGenericOnOff(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic OnOff state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericOnOffStatusReceived(ble_mesh_generic_client_evt_onoff_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param onOff     the target value of the Generic OnOff state
     * @param tid       transaction Identifier.
     * @param transTime transition time
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setGenericOnOff(short dstAddr, byte ttl, byte onOff, byte tid, byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setGenericOnOff(dstAddr, ttl, onOff, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Level state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getGenericLevel(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getGenericLevel(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Level state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param level     the target value of the Generic Level state
     * @param tid       transaction Identifier.
     * @param transTime transition time
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setGenericLevel(short dstAddr, byte ttl, short level, byte tid, byte transTime, byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setGenericLevel(dstAddr, ttl, level, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Level Delta state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param delta     the target Delta value of the Generic Level state
     * @param tid       transaction Identifier.
     * @param transTime transition time
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setGenericLevelDelta(short dstAddr, byte ttl,
                                    int delta, byte tid, byte transTime,
                                    byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setGenericLevelDelta(dstAddr, ttl, delta, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Level Move state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLevelStatusReceived(ble_mesh_generic_client_evt_level_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param move     the target Move value of the Generic Level state
     * @param tid       transaction Identifier.
     * @param transTime transition time
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setGenericLevelMove(short dstAddr, byte ttl,
                                   int move, byte tid, byte transTime,
                                   byte delay, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setGenericLevelMove(dstAddr, ttl, move, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Default Transition Time state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getDefaultTransitionTime(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getDefaultTransitionTime(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Default Transition Time state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericDefaultTransitionTimeStatusReceived(ble_mesh_generic_client_evt_default_transition_time_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param transTime transition time
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setDefaultTransitionTime(short dstAddr, byte ttl,
                                        byte transTime, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setDefaultTransitionTime(dstAddr, ttl, transTime, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic OnPowerUp state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getOnPowerUp(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getOnPowerUp(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic OnPowerUp state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericOnPowerUpStatusReceived(ble_mesh_generic_client_evt_on_power_up_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param state      is the value of the value of the Generic OnPowerUp state.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setOnPowerUp(short dstAddr, byte ttl,
                            ENUM_DEF.ble_mesh_model_generic_on_powerup_t state, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_model_generic_on_powerup_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setOnPowerUp(dstAddr, ttl, state_id, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Power Actual state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getPowerLevelActual(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getPowerLevelActual(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Power Actual state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerLevelStatusReceived(ble_mesh_generic_client_evt_power_level_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param actual     the target value of the Generic Power Actual state.
     * @param tid       transaction Identifier.
     * @param transTime transition time
     * @param delay     delay time. If the Transition Time field is present, the Delay field shall also be present; otherwise these fields shall not be present.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setPowerLevelActual(short dstAddr, byte ttl,
                                   short actual, byte tid, byte transTime,
                                   byte delay, boolean reliable){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setPowerLevelActual(dstAddr, ttl, actual, tid, transTime, delay, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Power Last state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerLastStatusReceived(ble_mesh_generic_client_evt_power_last_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getPowerLevelLast(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getPowerLevelLast(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Power Default state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getPowerLevelDefault(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getPowerLevelDefault(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Power Level Default state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerDefaultStatusReceived(ble_mesh_generic_client_evt_power_default_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param defaultValue     the target Default value of the Power Level state.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setPowerLevelDefault(short dstAddr, byte ttl,
                                    short defaultValue, boolean reliable){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setPowerLevelDefault(dstAddr, ttl, defaultValue, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Power Range state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getPowerLevelRange(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getPowerLevelRange(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set Generic Power Range state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericPowerRangeStatusReceived(ble_mesh_generic_client_evt_power_range_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl       the received TTL value.
     * @param rangeMin     the target Min value of the Power Level range.
     * @param rangeMax     the target Max value of the Power Level range.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setPowerLevelRange(short dstAddr, byte ttl,
                                  short rangeMin, short rangeMax,
                                  boolean reliable){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setPowerLevelRange(dstAddr, ttl, rangeMin, rangeMax, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get Generic Battery state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericBatteryStatusReceived(ble_mesh_generic_client_evt_battery_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getBattery(short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getBattery(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the global related fields of the Generic Location state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLocation(short dstAddr, byte ttl, byte location){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLocation(dstAddr, ttl, location);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the global related fields of the Generic Location state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLocationGlobalStatusReceived(ble_mesh_generic_client_evt_global_location_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @param altitude     the value of the Generic Location Altitude state.
     * @param latitude    the value of the Generic Location Latitude state.
     * @param longitude     the value of the Generic Location Longitude state.
     * @param reliable     reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLocationGlobal(short dstAddr, byte ttl,
                                 int altitude, int latitude,
                                 int longitude, boolean reliable){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLocationGlobal(dstAddr, ttl, altitude, latitude, longitude, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set the local related fields of the Generic Location state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericLocationLocalStatusReceived(ble_mesh_generic_client_evt_local_location_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param ttl     the received TTL value.
     * @param north    the value of the Generic Location North state.
     * @param east    the value of the Generic Location East state.
     * @param altitude     the value of the Generic Location Altitude state.
     * @param floorNumber    the value of the Generic Location FloorNumber state.
     * @param uncertainty     the value of the Generic Location Uncertainty state.
     * @param reliable     reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setLocationLocal(short dstAddr, byte ttl,
                                short north, short east,
                                short altitude, byte floorNumber,
                                byte uncertainty, boolean reliable){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setLocationLocal(
                    dstAddr, ttl, north, east, altitude, floorNumber, uncertainty, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }


    /**
     * Sends a request to get the list of Generic User/Admin/Manufacturer Property states.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericUserPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t)}
     * , {@link MeshGenericOnOffModelListener#onMeshGenericAdminPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t)}
     * and {@link MeshGenericOnOffModelListener#onMeshGenericManufacturerPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t)}
     *
     * @param type  is the target value of Device property type.
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getProperties(ENUM_DEF.ble_mesh_device_property_type_t type,  short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int type_id = Arrays.asList(ENUM_DEF.ble_mesh_device_property_type_t.values()).indexOf(type);
            ret = AirohaMesh.getInstance().getProperties(type_id, dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the list of Generic Client Property states.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericClientPropertiesStatusReceived(ble_mesh_generic_client_evt_properties_status_t)}
     *
     * @param propertyId    is a starting Client Property ID present within an element.
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getClientProperties(short propertyId, short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getClientProperties(propertyId, dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get the Generic User/Admin/Manufacturer Property state.<p>
     * The result is reported asynchronously through {@link MeshGenericOnOffModelListener#onMeshGenericUserPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t)}
     * , {@link MeshGenericOnOffModelListener#onMeshGenericAdminPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t)}
     * , and {@link MeshGenericOnOffModelListener#onMeshGenericManufacturerPropertyStatusReceived(ble_mesh_generic_client_evt_property_status_t)}
     *
     * @param type  is the target value of Device property type.
     * @param propertyId    is a starting Client Property ID present within an element.
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getProperty(ENUM_DEF.ble_mesh_device_property_type_t type,  short propertyId,
                           short dstAddr, byte ttl){
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int type_id = Arrays.asList(ENUM_DEF.ble_mesh_device_property_type_t.values()).indexOf(type);
            ret = AirohaMesh.getInstance().getProperty(type_id, propertyId, dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }
}
