package com.omnicrola.voxel.terrain;

import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.terrain.build.TerrainQuadFactory;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;

/**
 * Created by Eric on 2/5/2016.
 */
public class ChunkHandlerFactory {

    private TerrainAdapter terrainAdapter;

    public ChunkHandlerFactory(TerrainAdapter terrainAdapter) {
        this.terrainAdapter = terrainAdapter;
    }

    public VoxelChunkHandler build() {
        MaterialRepository materialRepository = terrainAdapter.getMaterialRepository();
        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, terrainAdapter.getWorldManager());
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(terrainAdapter, voxelChunkRebuilder);
        return voxelChunkHandler;
    }
}
