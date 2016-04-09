package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.terrain.TerrainManager;

import java.util.concurrent.Callable;

/**
 * Created by Eric on 4/8/2016.
 */
public class TerrainGeneratorTaskFactory implements ILoadingTaskFactory {

    private TerrainManager terrainManager;
    
    public TerrainGeneratorTaskFactory(TerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    @Override
    public Callable<LevelData> build(LevelData levelData) {
        return new TerrainGeneratorTask(levelData, terrainManager);
    }
}
