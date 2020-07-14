package com.airoha.libmesh.listener;

public interface MeshParamUpdateListener {

    /**
     * Callback for {@link com.airoha.btdlib.core.MeshParam#updateIvIndex(short, boolean)}. <p>
     * SDK user can handle the followup by overriding this.
     *
     */
    void onMeshIvUpdated (int iv_index, byte state);
}
