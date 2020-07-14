package com.airoha.libmeshparam;

/**
 * The input parameter structure for mesh provision.
 */

public class PROV_INPUT_DATA {
    /** Network key value */
    public byte[] netkey;
    /** Network key index */
    public short netkey_index;
    /** IV Index */
    public int iv_index;
    /** Unicast address */
    public short address;
    /** Flags indicate the current states of the IV update and key refresh. */
    public byte flags;                 
}
