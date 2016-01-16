package com.omnicrola.voxel.main.init;

import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class Bootstrapper {
    public static VoxelGameLauncher bootstrap() {
        return new VoxelGameLauncher(new VoxelGameEngine());
    }
}
