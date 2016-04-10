package com.omnicrola.voxel.data.level.load;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.terrain.VoxelChunkHandlerFactory;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;

/**
 * Created by Eric on 4/8/2016.
 */
public class TerrainGeneratorTaskFactory implements ILoadingTaskFactory {

    private final AssetManager assetManager;

    public TerrainGeneratorTaskFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public AbstractLoadTask build(LevelData levelData) {
        VoxelChunkHandlerFactory voxelChunkHandlerFactory = new VoxelChunkHandlerFactory(assetManager);
        VoxelTerrainGenerator voxelTerrainGenerator = new VoxelTerrainGenerator(new PerlinNoiseGenerator());
        TerrainGeneratorTask terrainGeneratorTask = new TerrainGeneratorTask(levelData, voxelTerrainGenerator, voxelChunkHandlerFactory);
        return terrainGeneratorTask;
    }
}
