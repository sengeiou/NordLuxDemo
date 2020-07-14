package com.airoha.libmeshparam.model.config;
import java.io.Serializable;

/**
 * The composition element structure.
 */

public class ble_mesh_composition_element_t implements Serializable {
    /** The location of this element. */
    public short loc;
    /** The number of SIG models in this element. */
    public byte num_s;
    /** The number of vendor models in this element. */
    public byte num_v;
    /** SIG model IDs. */
    public short[] Sig_models;
    /** Vendor model IDs. */
    public int[] vendor_models;   
}
