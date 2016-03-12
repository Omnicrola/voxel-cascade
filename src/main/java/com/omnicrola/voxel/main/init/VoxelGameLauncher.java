package com.omnicrola.voxel.main.init;

import com.omnicrola.util.Dependency;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameLauncher {

    @Dependency("GameEngine")
    private VoxelGameEngine gameEngine;

    public VoxelGameLauncher(VoxelGameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    public void launch() {
        this.gameEngine.start();
    }

    public VoxelGameEngine getGame() {
        return gameEngine;
    }
}
