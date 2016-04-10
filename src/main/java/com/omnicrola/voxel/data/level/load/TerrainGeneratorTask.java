package com.omnicrola.voxel.data.level.load;

import com.omnicrola.voxel.data.level.LevelData;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.VoxelChunkHandlerFactory;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.build.MultithreadedChunkRebuildStrategy;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Arrays;

/**
 * Created by omnic on 4/9/2016.
 */
public class TerrainGeneratorTask extends AbstractLoadTask {
    private VoxelTerrainGenerator voxelTerrainGenerator;
    private VoxelChunkHandlerFactory voxelChunkHandlerFactory;
    private float progress;

    public TerrainGeneratorTask(LevelData levelData,
                                VoxelTerrainGenerator voxelTerrainGenerator,
                                VoxelChunkHandlerFactory voxelChunkHandlerFactory) {
        super(levelData);
        this.voxelTerrainGenerator = voxelTerrainGenerator;
        this.voxelChunkHandlerFactory = voxelChunkHandlerFactory;
    }

    @Override
    protected String getTaskName() {
        return "Generate Terrain";
    }

    @Override
    protected void performLoading() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));

        TerrainDefinition terrainDefinition = this.levelData.levelDefinition.getTerrain();
        VoxelChunkHandler voxelChunkHandler = voxelChunkHandlerFactory.build(voxelTypeLibrary);

        this.voxelTerrainGenerator.generate(terrainDefinition, voxelChunkHandler);
        this.progress = 0.5f;
        voxelChunkHandler.update(MultithreadedChunkRebuildStrategy.INSTANCE);

        levelData.terrain = voxelChunkHandler;
    }

    @Override
    public double percentDone() {
        return (this.progress + MultithreadedChunkRebuildStrategy.INSTANCE.percentComplete()) / 2f;
    }
}
