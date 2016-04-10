package com.omnicrola.voxel.data.level.load;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.terrain.VoxelChunkHandlerFactory;
import com.omnicrola.voxel.terrain.build.ITerrainOperation;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.build.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.build.operations.AddHalfblockEdgesOperation;
import com.omnicrola.voxel.terrain.build.operations.CreateRandomTerrainOperation;

import java.util.ArrayList;

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
        ArrayList<ITerrainOperation> terrainOperations = createTerrainOperations();
        VoxelTerrainGenerator voxelTerrainGenerator = new VoxelTerrainGenerator(terrainOperations);
        TerrainGeneratorTask terrainGeneratorTask = new TerrainGeneratorTask(levelData, voxelTerrainGenerator, voxelChunkHandlerFactory);
        return terrainGeneratorTask;
    }

    private ArrayList<ITerrainOperation> createTerrainOperations() {
        ArrayList<ITerrainOperation> terrainOperations = new ArrayList<>();
        terrainOperations.add(new CreateRandomTerrainOperation(new PerlinNoiseGenerator()));
        terrainOperations.add(new AddHalfblockEdgesOperation());
        return terrainOperations;
    }
}
