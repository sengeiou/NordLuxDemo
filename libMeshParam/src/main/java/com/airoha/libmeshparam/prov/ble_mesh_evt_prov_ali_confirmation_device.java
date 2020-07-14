package com.airoha.libmeshparam.prov;

import java.io.Serializable;

/**
 * Event parameter of provision confirmation.
 */
public class ble_mesh_evt_prov_ali_confirmation_device implements Serializable{
    public byte[] confirmation_key;
    public byte[] device_confirmation;
    public byte[] device_random;
}
