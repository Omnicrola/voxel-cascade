package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 4/10/2016.
 */
public interface ITerrainOperation {
    void perform(TerrainDefinition terrainDefinition, VoxelChunkHandler voxelChunkHandler);
}
