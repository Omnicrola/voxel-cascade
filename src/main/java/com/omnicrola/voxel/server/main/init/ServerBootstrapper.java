package com.omnicrola.voxel.server.main.init;

import com.omnicrola.voxel.server.main.VoxelServerEngine;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerBootstrapper {

    public static VoxelServerLauncher bootstrap() {
        VoxelServerEngine voxelCascadeServer = new VoxelServerEngine();
        return new VoxelServerLauncher(voxelCascadeServer);
    }
}
