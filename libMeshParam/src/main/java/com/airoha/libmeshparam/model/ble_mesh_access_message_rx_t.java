package com.airoha.libmeshparam.model;

/**
 * This structure defines the received access message.
 */

public class ble_mesh_access_message_rx_t {
    /**The operation code information. */
    public ble_mesh_access_opcode_t opcode;
    /**The received message buffer. */
    public byte[] buffer;
    /**The length of received message. */
    public short length;
    /**The metadata of this message. */
    public ble_mesh_access_message_rx_meta_t meta_data;   
}
