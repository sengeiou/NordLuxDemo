package com.airoha.libmeshparam.prov;

import java.io.Serializable;

/**
 * Provisioning capabilities structure.
 */

public class ble_mesh_prov_capabilities_t implements Serializable{
    /** Number of elements supported by the device */
    public byte number_of_elements;
    /** Supported algorithms and other capabilities */
    public short algorithms;
    /** Supported public key types */
    public byte public_key_type;
    /** Supported static OOB types */
    public byte static_oob_type;
    /** Maximum size of output OOB supported */
    public byte output_oob_size;
    /** Supported output OOB actions */
    public short output_oob_action;
    /** Maximum size in octets of input OOB supported */
    public byte input_oob_size;
    /** Supported input OOB actions */
    public short input_oob_action;
}
