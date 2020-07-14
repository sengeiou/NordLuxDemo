package com.airoha.libmeshparam;

/**
 * Enumeration for each command/event.
 */
public class ENUM_DEF {

    public enum CMD_EVENT_ID {
        /** Cmd for GATT state update. */
        ANDROID_EVT_SERVICE_CONNECTED,
        /** Cmd for GATT state update. */
        ANDROID_EVT_SERVICE_DISCONNECTED,
        /** Cmd for mesh data processing.  */
        ANDROID_EVT_SERVICE_DATA_IN,
        /** Cmd for MTU state update. */
        ANDROID_EVT_MTU_CHANGE,
        /** Cmd for scan adv data processing.  */
        ANDROID_EVT_ADV_REPORT,

        /**Event for mesh initialization complete. */
        BLE_MESH_EVT_INIT_DONE,
        /**< Event for redeiving the link open message sent from a provisioner. */
        BLE_MESH_EVT_PROV_LINK_OPEN,
        /**< Event for receiving the invite message sent from a provisioner. */
        BLE_MESH_EVT_PROV_INVITE,
        /**Event for receiving the capabilites message sent from a provisionee. */
        BLE_MESH_EVT_PROV_CAPABILITIES,
        /**Event for requesting public key, use #ble_mesh_provision_provide_oob_public_key to set public key. */
        BLE_MESH_EVT_PROV_REQUEST_OOB_PUBLIC_KEY,
        /**Event for requesting authentication value, use #ble_mesh_provision_provide_oob_auth_value to set authentication value. */
        BLE_MESH_EVT_PROV_REQUEST_OOB_AUTH_VALUE,
        /**Event for showing public key. After showing, please use #ble_mesh_provision_provide_oob_public_key to set public key. */
        BLE_MESH_EVT_PROV_SHOW_OOB_PUBLIC_KEY,
        /**Event for showing authentication value. After showing, please use #ble_mesh_provision_provide_oob_auth_value to set authentication value. */
        BLE_MESH_EVT_PROV_SHOW_OOB_AUTH_VALUE,
        /**Event for mesh provisioning done. */
        BLE_MESH_EVT_PROV_DONE,
        /**Event for scanned mesh unprovidioned device. */
        BLE_MESH_EVT_PROV_SCAN_UD_RESULT,
        /**Event for mesh configuration reset. */
        BLE_MESH_EVT_CONFIG_RESET,
        /**Event for mesh friendship status change. */
        BLE_MESH_EVT_FRIENDSHIP_STATUS,
        /**Event for mesh LPN receiving friend offer. */
        BLE_MESH_EVT_LPN_FRIEND_OFFER,
        /**Event for mesh LPN receiving friend subscription list confirm. */
        BLE_MESH_EVT_LPN_FRIEND_SUBSCRIPTION_LIST_CONFRIM,
        /**Event for mesh heartbeat. */
        BLE_MESH_EVT_HEARTBEAT,
        /**Event for mesh IV index update. */
        BLE_MESH_EVT_IV_UPDATE,
        /**Event for mesh key refresh. */
        BLE_MESH_EVT_KEY_REFRESH,
        /**< Event for the mesh bearer GATT status. */
        BLE_MESH_EVT_BEARER_GATT_STATUS,
        /**< Event for the mesh bearer ADV status. */
        BLE_MESH_EVT_BEARER_ADV_STATUS,
        /**< Event for the mesh low power status. */
        BLE_MESH_EVT_LOW_POWER_STATUS,
    }

    public enum config_client_event_id_t {
        CONFIG_CLIENT_EVT_BEACON_STATUS,            /**< Event for beacon status. */
        CONFIG_CLIENT_EVT_COMPOSITION_DATA_STATUS,  /**< Event for composition data status. */
        CONFIG_CLIENT_EVT_DEFAULT_TTL_STATUS,       /**< Event for default TTL status. */
        CONFIG_CLIENT_EVT_GATT_PROXY_STATUS,        /**< Event for GATT proxy status. */
        CONFIG_CLIENT_EVT_FRIEND_STATUS,            /**< Event for friend status. */
        CONFIG_CLIENT_EVT_MODEL_PUBLICATION_STATUS, /**< Event for model publication status. */
        CONFIG_CLIENT_EVT_MODEL_SUBSCRIPTION_STATUS,    /**< Event for model subscription status. */
        CONFIG_CLIENT_EVT_NETWORK_TRANSMIT_STATUS,      /**< Event for network transmit status. */
        CONFIG_CLIENT_EVT_RELAY_STATUS,                 /**< Event for relay status. */
        CONFIG_CLIENT_EVT_MODEL_SUBSCRIPTION_LIST,      /**< Event for model subscription status. */
        CONFIG_CLIENT_EVT_LPN_POLL_TIMEOUT_STATUS,  /**< Event for low power node pull timeout status. */
        CONFIG_CLIENT_EVT_NETKEY_LIST,              /**< Event for network key list. */
        CONFIG_CLIENT_EVT_NETKEY_STATUS,            /**< Event for network key status. */
        CONFIG_CLIENT_EVT_APPKEY_LIST,              /**< Event for application key list. */
        CONFIG_CLIENT_EVT_APPKEY_STATUS,            /**< Event for application key status. */
        CONFIG_CLIENT_EVT_MODEL_APP_STATUS,         /**< Event for model app status. */
        CONFIG_CLIENT_EVT_MODEL_APP_LIST,           /**< Event for model app list. */
        CONFIG_CLIENT_EVT_NODE_IDENTITY_STATUS,     /**< Event for node identity status. */
        CONFIG_CLIENT_EVT_NODE_RESET_STATUS,        /**< Event for node reset status. */
        CONFIG_CLIENT_EVT_KEY_REFRESH_PHASE_STATUS, /**< Event for key refresh phase status. */
        CONFIG_CLIENT_EVT_HEARTBEAT_PUBLICATION_STATUS, /**< Event for heartbeat publication status. */
        CONFIG_CLIENT_EVT_HEARTBEAT_SUBSCRIPTION_STATUS /**< Event for heartbeat subscription status. */
    }

    public enum ble_mesh_status_code_t {
        BLE_MESH_SUCCESS,           /**< success */
        BLE_MESH_ERROR_OOM,             /**< no memory */
        BLE_MESH_ERROR_NULL,            /**< parameter is null pointer */
        BLE_MESH_ERROR_INVALID_ADDR,    /**< invalid address */
        BLE_MESH_ERROR_INVALID_TTL,     /**< invalid TTL value */
        BLE_MESH_ERROR_INVALID_KEY,     /**< invalid key index */
        BLE_MESH_ERROR_NOT_INIT,        /**< mesh core is not initialized */
        BLE_MESH_ERROR_INVALID_STATE,   /**< invalid state */
        BLE_MESH_ERROR_INVALID_ROLE,    /**< invalid role */
        BLE_MESH_ERROR_FAIL,            /**< operation failed */
        BLE_MESH_ERROR_INVALID_PARAM    /**< invalid parameter */
    }

    /* Provisioning PDU Type */
    public enum ble_mesh_prov_ali_evt_id_t {
        ALI_PROVISIONING_DISCOVERY,
        ALI_PROVISIONING_START,
        ALI_PROVISIONING_RESPONSE,
        ALI_PROVISIONING_CONFIRMATION_CLOUD,
        ALI_PROVISIONING_CONFIRMATION_DEVICE,
        ALI_PROVISIONING_AUTHENTICATION_RESULT,
        ALI_PROVISIONING_PROVISIONING_COMPLETE,
    }

    public enum ble_mesh_generic_client_event_id_t{
        BLE_MESH_GENERIC_CLIENT_EVT_ONOFF_STATUS,                    /**< Event for generic onoff status. */
        BLE_MESH_GENERIC_CLIENT_EVT_LEVEL_STATUS,                    /**< Event for generic level status. */
        BLE_MESH_GENERIC_CLIENT_EVT_DEFAULT_TRANSITION_TIME_STATUS,  /**< Event for generic default transition time status. */
        BLE_MESH_GENERIC_CLIENT_EVT_ONPOWERUP_STATUS,                /**< Event for generic onpowerup status. */
        BLE_MESH_GENERIC_CLIENT_EVT_POWER_LEVEL_STATUS,              /**< Event for generic power level status. */
        BLE_MESH_GENERIC_CLIENT_EVT_POWER_LAST_STATUS,               /**< Event for generic power last status. */
        BLE_MESH_GENERIC_CLIENT_EVT_POWER_DEFAULT_STATUS,            /**< Event for generic power default status. */
        BLE_MESH_GENERIC_CLIENT_EVT_POWER_RANGE_STATUS,              /**< Event for generic power range status. */
        BLE_MESH_GENERIC_CLIENT_EVT_BATTERY_STATUS,                  /**< Event for generic battery status. */
        BLE_MESH_GENERIC_CLIENT_EVT_LOCATION_GLOBAL_STATUS,          /**< Event for generic location global status. */
        BLE_MESH_GENERIC_CLIENT_EVT_LOCATION_LOCAL_STATUS,           /**< Event for generic location local status. */
        BLE_MESH_GENERIC_CLIENT_EVT_MANUFACTURER_PROPERTIES_STATUS,  /**< Event for generic manufacturer properties status. */
        BLE_MESH_GENERIC_CLIENT_EVT_MANUFACTURER_PROPERTY_STATUS,    /**< Event for generic manufacturer property status. */
        BLE_MESH_GENERIC_CLIENT_EVT_ADMIN_PROPERTIES_STATUS,         /**< Event for generic admin properties status. */
        BLE_MESH_GENERIC_CLIENT_EVT_ADMIN_PROPERTY_STATUS,           /**< Event for generic admin property status. */
        BLE_MESH_GENERIC_CLIENT_EVT_USER_PROPERTIES_STATUS,          /**< Event for generic user properties status. */
        BLE_MESH_GENERIC_CLIENT_EVT_USER_PROPERTY_STATUS,            /**< Event for generic user property status. */
        BLE_MESH_GENERIC_CLIENT_EVT_CLIENT_PROPERTIES_STATUS,        /**< Event for generic client properties status. */
    }

    public enum ble_mesh_lightness_client_event_id_t {
        BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_LIGHTNESS_STATUS,    /**< Event for lightness status. */
        BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_LINEAR_STATUS,       /**< Event for lightness linear status. */
        BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_LAST_STATUS,         /**< Event for lightness last status. */
        BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_DEFAULT_STATUS,      /**< Event for lightness default status. */
        BLE_MESH_LIGHT_LIGHTNESS_CLIENT_EVT_RANGE_STATUS         /**< Event for lightness range status. */
    }

    public enum ble_mesh_ctl_client_event_id_t{
        BLE_MESH_LIGHT_CTL_CLIENT_EVT_STATUS,                    /**< Event for CTL status. */
        BLE_MESH_LIGHT_CTL_CLIENT_EVT_TEMPERATURE_RANGE_STATUS,  /**< Event for CTL temperature range status. */
        BLE_MESH_LIGHT_CTL_CLIENT_EVT_TEMPERATURE_STATUS,        /**< Event for CTL temperature status. */
        BLE_MESH_LIGHT_CTL_CLIENT_EVT_DEFAULT_STATUS             /**< Event for CTL default status. */
    }

    public enum ble_mesh_hsl_client_event_id_t{
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_HUE_STATUS,        /**< Event for HSL hue status. */
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_SATURATION_STATUS, /**< Event for HSL saturation status. */
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_STATUS,            /**< Event for HSL status. */
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_TARGET_STATUS,     /**< Event for HSL target status. */
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_DEFAULT_STATUS,    /**< Event for HSL default status. */
        BLE_MESH_LIGHT_HSL_CLIENT_EVT_RANGE_STATUS       /**< Event for HSL range status. */
    }

    public enum ble_mesh_health_client_event_id_t{
        BLE_MESH_HEALTH_CLIENT_EVT_CURRENT_STATUS,      /**< Event for health current status. */
        BLE_MESH_HEALTH_CLIENT_EVT_FAULT_STATUS,        /**< Event for health fault status. */
        BLE_MESH_HEALTH_CLIENT_EVT_PERIOD_STATUS,       /**< Event for health period status. */
        BLE_MESH_HEALTH_CLIENT_EVT_ATTENTION_STATUS     /**< Event for health attention status. */
    }

    public enum ble_mesh_feature_state_t{
        BLE_MESH_FEATURE_STATE_DISABLED,       /**< Feature is supported, but disabled. */
        BLE_MESH_FEATURE_STATE_ENABLED,        /**< Feature is supported and enabled. */
        BLE_MESH_FEATURE_STATE_NOT_SUPPORTED   /**< Feature is not supported. */
    }

    /*! @brief Generic OnPowerUp state */
    public enum ble_mesh_model_generic_on_powerup_t{
        GENERIC_ON_POWERUP_OFF,        /**< Off. After being powered up, the element is in an off state. */
        GENERIC_ON_POWERUP_DEFAULT,    /**< After being powered up, the element is in an On state and uses default state values. */
        GENERIC_ON_POWERUP_RESTORE,    /**< If a transition was in progress when powered down, the element restores the target state when powered up. Otherwise the element restores the state it was in when powered down. */
    }

    /** @brief Device property type */
    public enum ble_mesh_device_property_type_t {
        NOT_SUPPORTED,
        BLE_MESH_DEVICE_PROPERTY_TYPE_USER,          /**< User accessible device property. */
        BLE_MESH_DEVICE_PROPERTY_TYPE_ADMIN,         /**< Administrator accessible device property. */
        BLE_MESH_DEVICE_PROPERTY_TYPE_MANUFACTURER,  /**< Manufacturer accessible device property. */
    }

    /** @brief Mesh address type */
    public enum ble_mesh_address_type_t {
        BLE_MESH_ADDRESS_TYPE_UNASSIGNED,   /**< unassigned address */
        BLE_MESH_ADDRESS_TYPE_UNICAST,          /**< unicast address */
        BLE_MESH_ADDRESS_TYPE_VIRTUAL,          /**< virtual address */
        BLE_MESH_ADDRESS_TYPE_GROUP,            /**< group address */
    }

    public  enum ADDR_TYPE {
        ble_addr_type_public,               ///< public address
        ble_addr_type_random_static,        ///< random static address
        ble_addr_type_random_resolvable,    ///< random resolvable addresss
        ble_addr_type_random_non_resolvable, ///< random non resolvable address
    }

    public enum ADV_TYPE {
        ble_report_type_ind,                 ///< Type for ADV_IND found (passive)
        ble_report_type_direct_ind,          ///< Type for ADV_DIRECT_IND found (passive)
        ble_report_type_scan_ind,         ///< Type for ADV_SCAN_IND found (passive)
        ble_report_type_nonconn_ind,        ///< Type for ADV_NONCONN_IND found (passive)
        ble_report_type_scan_rsp            ///< Type for SCAN_RSP found (active)
    }
}