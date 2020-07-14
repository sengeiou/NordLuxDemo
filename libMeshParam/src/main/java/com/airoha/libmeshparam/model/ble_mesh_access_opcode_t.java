package com.airoha.libmeshparam.model;

/**
 * The access message operation code information
 */

public class ble_mesh_access_opcode_t {
    /** Operation code. */
    public short opcode;
    /** Company id, use #MESH_MODEL_COMPANY_ID_NONE if this is a SIG access message */
    public short company_id;   
}
