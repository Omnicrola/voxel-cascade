package com.omnicrola.voxel.server.main.init;

import com.jme3.bullet.BulletAppState;
import com.omnicrola.voxel.server.main.VoxelServer;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerBootstrapper {
    public static VoxelServerLauncher bootstrap() {
        ServerInitializer serverInitializer = new ServerInitializer();
        BulletAppState bulletAppState = new BulletAppState();
        VoxelServer voxelCascadeServer = new VoxelServer(bulletAppState, serverInitializer);
        return new VoxelServerLauncher(voxelCascadeServer);
    }
}
