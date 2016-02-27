package com.omnicrola.voxel.engine;

/**
 * Created by omnic on 2/27/2016.
 */
public class ShutdownHandler implements IShutdown {
    private VoxelGameEngine voxelGameEngine;

    public ShutdownHandler(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void shutdownAndExit() {
        this.voxelGameEngine.stop();
    }
}
