package com.omnicrola.voxel.server.main.init;

import com.jme3.system.JmeContext;
import com.omnicrola.voxel.server.main.VoxelServer;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerLauncher {
    private VoxelServer voxelCascadeServer;

    public VoxelServerLauncher(VoxelServer voxelCascadeServer) {
        this.voxelCascadeServer = voxelCascadeServer;
    }

    public void launch() {
        this.voxelCascadeServer.start(JmeContext.Type.Headless);
    }
}
