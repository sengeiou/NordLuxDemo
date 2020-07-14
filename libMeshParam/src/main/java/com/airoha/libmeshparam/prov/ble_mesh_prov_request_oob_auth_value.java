package com.airoha.libmeshparam.prov;

import java.io.Serializable;

/**
 * Provisioning request oob value structure.
 */

public class ble_mesh_prov_request_oob_auth_value implements Serializable{
    /**< Authentication Method used. */
    public byte method;
    /**< Selected Output OOB Action or Input OOB Action or 0x00. */
    public byte action;
    /**< Size of the Output OOB used or size of the Input OOB used or 0x00. */
    public byte size;
}
