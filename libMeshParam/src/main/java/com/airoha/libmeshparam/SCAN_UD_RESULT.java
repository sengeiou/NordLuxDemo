package com.airoha.libmeshparam;

/**
 * Created by MTK60348 on 2018/5/11.
 */

/// ble_mesh_prov_unprovisioned_device_t
public class SCAN_UD_RESULT {
    public byte[] uuid;                     /**< The unprovisioned device UUID. */
    public short oob_info;                  /**< The OOB information of the unprovisioned device. */
    public byte[] uri_hash;                  /**< The Uri hash value of the unprovisioned device; Can be NULL. */
}
