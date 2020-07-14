package com.airoha.libmeshparam.prov;

import java.io.Serializable;

/**
 * Event parameter of provision response.
 */
public class ble_mesh_evt_prov_ali_response implements Serializable{
    public byte[] confirmation_key;
    public byte[] provisioner_random;
}
