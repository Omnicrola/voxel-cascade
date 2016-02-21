package com.omnicrola.voxel.server.main.init;

import com.jme3.system.JmeContext;
import com.omnicrola.voxel.server.main.VoxelServerEngine;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerLauncher {
    private VoxelServerEngine voxelCascadeServer;

    public VoxelServerLauncher(VoxelServerEngine voxelCascadeServer) {
        this.voxelCascadeServer = voxelCascadeServer;
    }

    public void launch() {
        this.voxelCascadeServer.start(JmeContext.Type.Headless);
    }
}
