package com.airoha.libmeshparam.model.config;

import java.io.Serializable;

/**
 * The composition data structure.
 */

public class ble_mesh_composition_data_t implements Serializable {
    /** Contains a 16-bit company identifier assigned by the Bluetooth SIG */
    public short cid;
    /** Contains a 16-bit vendor-assigned product identifier */
    public short pid;
    /** Contains a 16-bit vendor-assigned product version identifier */
    public short vid;
    /** Contains a 16-bit value representing the minimum number of replay protection list entries in a device */
    public short crpl;
    /** Contains a bit field indicating the device features */
    public short features;
    /** Contains a double-linked list of #ble_mesh_composition_element_t */
    public ble_mesh_composition_element_t[] elements;
    /** The number of elements */
    public short element_length;
}
