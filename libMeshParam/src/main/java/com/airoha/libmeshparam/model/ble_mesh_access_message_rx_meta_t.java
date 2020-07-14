package com.airoha.libmeshparam.model;

/**
 * This structure defines the meatdata of a received access message.
 */

public class ble_mesh_access_message_rx_meta_t {
    /** The source address of this message. */
    public short src_addr;
    /** The destination address of this message */
    public short dst_addr;
    /** The application key index used for this message. */
    public short appkey_index;
    /** The network key index used for this message. */
    public short netkey_index;
    /** The RSSI value. */
    public byte rssi;
    /** The received TTL value. */
    public byte ttl;
}
