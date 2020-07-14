package com.airoha.libmesh.core;

import android.util.Log;

import com.airoha.libmesh.listener.MeshConfigurationModelListener;
import com.airoha.libmeshparam.ENUM_DEF;
import com.airoha.libmeshparam.model.config.config_client_evt_appkey_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_appkey_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_beacon_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_composition_data_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_default_ttl_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_friend_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_gatt_proxy_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_heartbeat_publication_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_heartbeat_subscription_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_key_refresh_phase_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_lpn_poll_timeout_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_app_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_app_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_publication_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_model_subscription_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_netkey_list_t;
import com.airoha.libmeshparam.model.config.config_client_evt_netkey_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_network_transmit_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_node_identity_status_t;
import com.airoha.libmeshparam.model.config.config_client_evt_relay_status_t;
import com.airoha.libnativemesh.AirohaMesh;

import java.util.Arrays;

import static com.airoha.libmesh.core.AirohaMeshMgr.NOT_INIT_ERROR;
import static com.airoha.libmesh.core.AirohaMeshMgr.TAG;

/**
 * Provide API to config Mesh Configuration Model
 */

public class MeshConfigurationModel{
    AirohaMeshMgr mAirohaMeshMgr = null;
    MeshConfigurationModelListenerMgr mMeshConfigurationModelListenerMgr = null;

    MeshConfigurationModel(AirohaMeshMgr mgr) {
        mAirohaMeshMgr = mgr;
        mMeshConfigurationModelListenerMgr = new MeshConfigurationModelListenerMgr();
    }

    public void addListener(String name, MeshConfigurationModelListener listener) {
        mMeshConfigurationModelListenerMgr.addListener(name, listener);
    }

    public void removeListener(String name) {
        mMeshConfigurationModelListenerMgr.removeListener(name);
    }
    /**
     * Add a configuration client model.
     *
     * @param elementIndex is the specified element for adding the configuration client model.
     * @return the handle of this added model if action is successful, otherwise is smaller than 0.
     */
    public int addConfigurationClient(short elementIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().addConfigurationClient(elementIndex);
        }
        return ret;
    }

    /**
     * Set the network key index. <p>
     *
     * @param netKeyIndex is network key index.
     */
    public int setNetKeyIndex(short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().setNetKeyIndex(netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a composition data get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigCompositionReceived(config_client_evt_composition_data_status_t)}
     *
     * @param dstAddr is the destination address.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getComposition(short dstAddr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_GET_COMPOSITION;
            ret = AirohaMesh.getInstance().getComposition(dstAddr);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a network key add request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param netKeyIndex is network key index.
     * @param netKey is the pointer to a 16-byte network key.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int addConfigNetKey(short dstAddr, short netKeyIndex, byte[] netKey) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().addConfigNetKey(dstAddr, netKeyIndex, netKey);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a network key update request.. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param netKeyIndex is network key index.
     * @param netKey is the pointer to a 16-byte network key.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int updateConfigNetKey(short dstAddr, short netKeyIndex, byte[] netKey) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().updateConfigNetKey(dstAddr, netKeyIndex, netKey);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a network key get request.. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetKeyListReceived(config_client_evt_netkey_list_t)}
     *
     * @param dstAddr is the destination address.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getConfigNetKey(short dstAddr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().getConfigNetKey(dstAddr);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a network key delete request.. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetKeyStatusReceived(config_client_evt_netkey_status_t)}
     *
     * @param dstAddr is the destination address.
     * @param netKeyIndex is network key index.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int deleteConfigNetKey(short dstAddr, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().deleteConfigNetKey(dstAddr, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends an application key add request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param netKeyIndex is network key index.
     * @param appKeyIndex is application key index.
     * @param appKey      is the pointer to a 16-byte application key.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int addConfigAppKey(short dstAddr, short netKeyIndex, short appKeyIndex, byte[] appKey) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().addConfigAppKey(dstAddr, netKeyIndex, appKeyIndex, appKey);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends an application key update request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param netKeyIndex is network key index.
     * @param appKeyIndex is application key index.
     * @param appKey      is the pointer to a 16-byte application key.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int updateConfigAppKey(short dstAddr, short netKeyIndex, short appKeyIndex, byte[] appKey) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().updateConfigAppKey(dstAddr, netKeyIndex, appKeyIndex, appKey);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends an application key get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigAppKeyListReceived(config_client_evt_appkey_list_t)}
     *
     * @param dstAddr     is the destination address.
     * @param netKeyIndex is network key index.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getConfigAppKey(short dstAddr, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().getConfigAppKey(dstAddr, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends an application key delete request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigAppKeyStatusReceived(config_client_evt_appkey_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param netKeyIndex is network key index.
     * @param appKeyIndex is application key index.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int deleteConfigAppKey(short dstAddr, short netKeyIndex, short appKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_ADD_APPKEY;
            ret = AirohaMesh.getInstance().deleteConfigAppKey(dstAddr, netKeyIndex, appKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a model application bind request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param appKeyIndex is application key index.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int bindConfigModelApp(short dstAddr, short elementAddr, short appKeyIndex, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL;
            ret = AirohaMesh.getInstance().bindConfigModelApp(dstAddr, elementAddr, appKeyIndex, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a model application unbind request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigModelAppStatusReceived(config_client_evt_model_app_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param appKeyIndex is application key index.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int unbindConfigModelApp(short dstAddr, short elementAddr, short appKeyIndex, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL;
            ret = AirohaMesh.getInstance().unbindConfigModelApp(dstAddr, elementAddr, appKeyIndex, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a model application get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigModelAppListReceived(config_client_evt_model_app_list_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getSigModelAppList(short dstAddr, short elementAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL;
            ret = AirohaMesh.getInstance().getSigModelApp(dstAddr, elementAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a model application get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigModelAppListReceived(config_client_evt_model_app_list_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getVendorModelAppList(short dstAddr, short elementAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BIND_MODEL;
            ret = AirohaMesh.getInstance().getVendorModelApp(dstAddr, elementAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a publication get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @param ttl    is the TTL value of this request message.
     * @param netkeyIdx     is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getModelPublication(short dstAddr, short elementAddr, int modelID,
                                   byte ttl, short netkeyIdx) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getModelPublication(
                    dstAddr, elementAddr, modelID, ttl, netkeyIdx);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     Send a publication set request.<p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigPublicationStatusReceived(config_client_evt_model_publication_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param pubAddr     is the value of the publish address.
     * @param appkeyIndex     is the index of the application key.
     * @param friendshipCredentialFlag     is the value of the Friendship Credential Flag.
     * @param publishTtl     is the TTL value for the outgoing messages.
     * @param publishPeriod     is the period for periodic status publishing.
     * @param retransmitCount     is the number of retransmissions for each published message.
     * @param retransmitIntervalSteps     is the number of 50-millisecond steps between retransmissions.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setModelPublication(short dstAddr, short elementAddr, short pubAddr, short appkeyIndex,
                                       boolean friendshipCredentialFlag, byte publishTtl, byte publishPeriod,
                                       byte retransmitCount, byte retransmitIntervalSteps, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setModelPublication(dstAddr, elementAddr, pubAddr, appkeyIndex,
                    friendshipCredentialFlag, publishTtl, publishPeriod, retransmitCount,
                    retransmitIntervalSteps, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription add request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param subAddr     is the address to add to the subscription list.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int addModelSubscription(short dstAddr, short elementAddr, short subAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().addModelSubscription(dstAddr, elementAddr, subAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription delete request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param subAddr     is the address to add to the subscription list.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int deleteModelSubscription(short dstAddr, short elementAddr, short subAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().deleteModelSubscription(dstAddr, elementAddr, subAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription delete request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int deleteAllModelSubscription(short dstAddr, short elementAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().deleteAllModelSubscription(dstAddr, elementAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription overwrite request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param subAddr     is the address to add to the subscription list.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int overwriteModelSubscription(short dstAddr, short elementAddr, short subAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().overwriteModelSubscription(dstAddr, elementAddr, subAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getSigModelSubscription(short dstAddr, short elementAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getSigModelSubscription(dstAddr, elementAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a subscription get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigSubscriptionStatusReceived(config_client_evt_model_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param elementAddr is the element address of the model.
     * @param modelID     is the model identifier.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getVendorModelSubscription(short dstAddr, short elementAddr, int modelID) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getVendorModelSubscription(dstAddr, elementAddr, modelID);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a secure network beacon state get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl is the TTL value of this request message.
     * @param netKeyIndex is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getBeacon(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getBeacon(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a secure network beacon state set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigBeaconStatusReceived(config_client_evt_beacon_status_t)}
     *
     * @param state means new secure network beacon state.
     * @param dstAddr     is the destination address.
     * @param ttl is the TTL value of this request message.
     * @param netKeyIndex is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setBeacon(ENUM_DEF.ble_mesh_feature_state_t state, short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_feature_state_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setBeacon(state_id, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a default TTL get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl is the TTL value of this request message.
     * @param netKeyIndex is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getDefaultTTL(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getDefaultTTL(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a default TTL set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigDefaultTtlStatusReceived(config_client_evt_default_ttl_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param defaultTtl    means the new default TTL value.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setDefaultTTL(short dstAddr, byte defaultTtl, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setDefaultTTL(dstAddr, defaultTtl, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a GATT proxy state get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getGattProxy(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getGattProxy(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a GATT proxy state set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigGattPorxyStatusReceived(config_client_evt_gatt_proxy_status_t)}
     *
     * @param state means new GATT proxy state.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setGattProxy(ENUM_DEF.ble_mesh_feature_state_t state, short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_feature_state_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setGattProxy(dstAddr, state_id, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a key refresh phase get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param targetNetkeyIdx is the network key index.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getKeyRefreshPhase(short dstAddr, short targetNetkeyIdx, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getKeyRefreshPhase(dstAddr, targetNetkeyIdx, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a key refresh phase set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigKeyRefreshPhaseStatusReceived(config_client_evt_key_refresh_phase_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param targetNetkeyIdx is the network key index.
     * @param transition is the new key refresh phase transition.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setKeyRefreshPhase(short dstAddr, short targetNetkeyIdx, byte transition,
                                  byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setKeyRefreshPhase(dstAddr, targetNetkeyIdx, transition, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a friend state get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigFriendStatusReceived(config_client_evt_friend_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getFriend(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getFriend(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a friend state set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigFriendStatusReceived(config_client_evt_friend_status_t)}
     *
     * @param state means new friend state.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setFriend(ENUM_DEF.ble_mesh_feature_state_t state, short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_feature_state_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setFriend(state_id, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a relay state get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigRelayStatusReceived(config_client_evt_relay_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getRelay(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getRelay(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Sends a relay state set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigRelayStatusReceived(config_client_evt_relay_status_t)}
     *
      * @param state means the new relay state.
     * @param retransmit_count means number of retransmissions on advertising bearer for each Network PDU relayed by the node.
     * @param retransmit_interval_steps means mumber of 10-millisecond steps between retransmissions.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setRelay(ENUM_DEF.ble_mesh_feature_state_t state,
                        byte retransmit_count, byte retransmit_interval_steps,
                        short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_feature_state_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setRelay(state_id, retransmit_count,
                    retransmit_interval_steps, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a node identity state get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t)}
     *
     * @param targetNetkeyIdx is network key index.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getNodeIdentity(short targetNetkeyIdx, short dstAddr,
                               byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getNodeIdentity(targetNetkeyIdx, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a node identity state set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNodeIdentityStatusReceived(config_client_evt_node_identity_status_t)}
     *
     * @param targetNetkeyIdx is network key index.
     * @param state means the new node identity state.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setNodeIdentity(short targetNetkeyIdx, ENUM_DEF.ble_mesh_feature_state_t state,
                               short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            int state_id = Arrays.asList(ENUM_DEF.ble_mesh_feature_state_t.values()).indexOf(state);
            ret = AirohaMesh.getInstance().setNodeIdentity(targetNetkeyIdx, state_id, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a heartbeat publication get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getHeartbeatPublication(short dstAddr,
                                       byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getHeartbeatPublication(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a heartbeat publication set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigHeartbeatPublicationStatusReceived(config_client_evt_heartbeat_publication_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setHeartbeatPublication(short pubDest, int count,
                                       byte periodLog, byte targetTtl,
                                       short features, short targetNetkeyIdx,
                                       short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setHeartbeatPublication(
                    pubDest, count, periodLog, targetTtl, features, targetNetkeyIdx,
                    dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a heartbeat subscription get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getHeartbeatSubscription(short dstAddr,
                                        byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getHeartbeatSubscription(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a heartbeat subscription set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigHeartbeatSubscriptionStatusReceived(config_client_evt_heartbeat_subscription_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setHeartbeatSubscription(short source, short destination,
                                        byte period_log, short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setHeartbeatSubscription(
                    source, destination, period_log,
                    dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a network transmit get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getNetworkTransmit(short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getNetworkTransmit(dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a network transmit set request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigNetworkTransmitStatusReceived(config_client_evt_network_transmit_status_t)}
     *
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int setNetworkTransmit(byte count, byte intervalSteps,
                                  short dstAddr, byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().setNetworkTransmit(
                    count, intervalSteps, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     *  Send a LPN poll timeout get request. <p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigLpnPollTimeoutStatusReceived(config_client_evt_lpn_poll_timeout_status_t)}
     *
     * @param lpnAddr is the LPN address.
     * @param dstAddr     is the destination address.
     * @param ttl   is the TTL value of this request message.
     * @param netKeyIndex    is the network key that is used for this request message.
     * @return the execution status {@link ENUM_DEF.ble_mesh_status_code_t}
     */
    public int getLpnPollTimeout(short lpnAddr, short dstAddr,
                                 byte ttl, short netKeyIndex) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_BUSY;
            ret = AirohaMesh.getInstance().getLpnPollTimeout(lpnAddr, dstAddr, ttl, netKeyIndex);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    /**
     * Send a beacon get request.<p>
     * [warning] This will make the node reset and be removed from the network.<p>
     * The result is reported asynchronously through {@link MeshConfigurationModelListener#onMeshConfigResetNodeStatusReceived(int)}
     *
     * @param dstAddr is the destination address.
     * @return the execution status {@link ENUM_DEF.CMD_EVENT_ID}
     */
    public int resetNode(short dstAddr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().resetNode(dstAddr);
            if (ret != 0) {
                mAirohaMeshMgr.mSwitchState = AirohaMeshMgr.SWITCH_STATE.SWITCH_SETTING_STATE_IDLE;
            }
        }
        return ret;
    }

    public int deleteDevKey(short dstAddr) {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        int ret = -1;
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            ret = AirohaMesh.getInstance().deleteDevKey(dstAddr);
        }
        return ret;
    }

    public void clearReplayProtection() {
        Log.d(TAG, new Object(){}.getClass().getEnclosingMethod().getName());
        if (!mAirohaMeshMgr.mIsMeshInitDone) {
            Log.e(TAG, NOT_INIT_ERROR);
        } else {
            AirohaMesh.getInstance().clearReplayProtection();
        }
    }
}
