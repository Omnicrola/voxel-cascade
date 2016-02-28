package com.omnicrola.voxel.engine;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.network.INetworkManager;

/**
 * Created by omnic on 2/27/2016.
 */
public class EngineShutdownHandler {
    public void shutdownAndExit(VoxelGameEngine voxelGameEngine) {
        AppStateManager stateManager = voxelGameEngine.getStateManager();
        ClientNetworkState clientNetworkState = stateManager.getState(ClientNetworkState.class);
        INetworkManager networkManager = clientNetworkState.getNetworkManager();
        networkManager.disconnect();
        networkManager.shutdownMultiplayer();
    }
}
