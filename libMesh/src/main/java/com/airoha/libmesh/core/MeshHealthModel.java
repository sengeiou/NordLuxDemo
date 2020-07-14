package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshHealthModelListener;
import com.airoha.libnativemesh.AirohaMesh;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_attention_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_fault_status_t;
import com.airoha.libmeshparam.model.health.ble_mesh_health_client_evt_period_status_t;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Health Model.
 */

public class MeshHealthModel {
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshHealthModelListenerMgr mMeshHealthModelListenerMgr = null;

    MeshHealthModel(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshHealthModelListenerMgr = new MeshHealthModelListenerMgr();
    }

    public void addListener(String name, MeshHealthModelListener listener) {
        mMeshHealthModelListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshHealthModelListenerMgr.removeListener(name);
    }

    /**
     * Add a Health client model.
     *
     * @param elementIndex is the specified element for adding the Health client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public short addHealthClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        short ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addHealthClient(elementIndex);
        }
        return ret;
    }

    /**
     * Sends a request to clear current Registered Fault.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @param companyId   is the Bluetooth assigned Company Identifier.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientClearFault(short dstAddr, byte ttl,
                                      short companyId, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientClearFault(dstAddr, ttl, companyId, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get current Registered Fault.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @param companyId   is the Bluetooth assigned Company Identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientGetFault(short dstAddr, byte ttl,
                                    short companyId) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientGetFault(dstAddr, ttl, companyId);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to invoke self-test procedure of an element.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthFaultStatusReceived(ble_mesh_health_client_evt_fault_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @param testId  is the identifier of a specific test to be performed.
     * @param companyId   is the Bluetooth assigned Company Identifier.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientTestFault(short dstAddr, byte ttl,
                                     byte  testId, short companyId,
                                     boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientTestFault(dstAddr, ttl, testId, companyId, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get current Health Period state.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientGetPeriod(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientGetPeriod(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set current Health Period state.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthPeriodStatusReceived(ble_mesh_health_client_evt_period_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @param fastPeriodDivisor      is the divider for the publish period.
     * @param reliable  reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientSetPeriod(short dstAddr, byte ttl,
                                     byte fastPeriodDivisor, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientSetPeriod(dstAddr, ttl, fastPeriodDivisor, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to get current Attention Timer.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientGetAttention(short dstAddr, byte ttl) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientGetAttention(dstAddr, ttl);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a request to set current Attention Timer.<p>
     * The result is reported asynchronously through {@link MeshHealthModelListener#onMeshHealthAttentionStatusReceived(ble_mesh_health_client_evt_attention_status_t)}
     *
     * @param dstAddr   is the destination address.
     * @param ttl     the received TTL value.
     * @param attention    is the value of the attention timer.
     * @param reliable   reliable is to send the request as a reliable message or not.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int healthClientSetAttention(short dstAddr, byte ttl,
                                        byte attention, boolean reliable) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().healthClientSetAttention(dstAddr, ttl, attention, reliable);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }
}
