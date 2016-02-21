package com.omnicrola.voxel.main.init;

import com.omnicrola.voxel.main.VoxelServer;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerLauncher {
    private VoxelServer voxelCascadeServer;

    public VoxelServerLauncher(VoxelServer voxelCascadeServer) {
        this.voxelCascadeServer = voxelCascadeServer;
    }

    public void launch() {
        this.voxelCascadeServer.start();
    }
}
