package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

import java.util.List;

/**
 * Created by Eric on 2/24/2016.
 */
public class VoxelTerrainGenerator {

    private List<ITerrainOperation> terrainOperations;

    public VoxelTerrainGenerator(List<ITerrainOperation> terrainOperations) {
        this.terrainOperations = terrainOperations;
    }

    public void generate(TerrainDefinition terrainDefinition, VoxelChunkHandler voxelChunkHandler) {
        this.terrainOperations.forEach(op -> op.perform(terrainDefinition, voxelChunkHandler));
    }

}
