package com.airoha.libmesh.listener;

import com.airoha.libmeshparam.model.ble_mesh_access_message_rx_t;

public interface MeshVendorModelListener {
    void OnMeshVendorModelMsgReceived(ble_mesh_access_message_rx_t msg);
}
