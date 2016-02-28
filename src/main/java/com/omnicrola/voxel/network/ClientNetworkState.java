package com.omnicrola.voxel.network;

import com.jme3.app.state.AbstractAppState;

/**
 * Created by Eric on 2/21/2016.
 */
public class ClientNetworkState extends AbstractAppState {

    private NetworkManager networkManager;

    public ClientNetworkState(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.networkManager.update(tpf);
    }

    @Override
    public void cleanup() {
        super.cleanup();
        this.networkManager.cleanup();
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }
}
