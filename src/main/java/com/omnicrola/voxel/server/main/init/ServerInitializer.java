package com.omnicrola.voxel.server.main.init;

import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.server.network.ServerNetworkState;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerInitializer {
    public void init(VoxelServerEngine voxelServer) {
        ServerNetworkState serverNetworkState = new ServerNetworkState();
        voxelServer.getStateManager().attach(serverNetworkState);
    }
}
