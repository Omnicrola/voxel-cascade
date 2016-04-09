package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.TerrainManager;

/**
 * Created by omnic on 4/9/2016.
 */
public class TerrainGeneratorTask extends AbstractLoadTask {
    private TerrainManager terrainManager;

    public TerrainGeneratorTask(LevelData levelData, TerrainManager terrainManager) {
        super(levelData);
        this.terrainManager = terrainManager;
    }

    @Override
    protected void performLoading() {
        TerrainDefinition terrainDefinition = this.levelData.levelDefinition.getTerrain();
        this.terrainManager.load(terrainDefinition);
        this.terrainManager.update(0f);
    }
}
