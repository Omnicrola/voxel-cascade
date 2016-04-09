package com.omnicrola.voxel.terrain;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.build.mesh.*;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

/**
 * Created by omnic on 4/9/2016.
 */
public class VoxelChunkHandlerFactory {
    private final AssetManager assetManager;

    public VoxelChunkHandlerFactory(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public VoxelChunkHandler build(VoxelTypeLibrary voxelTypeLibrary) {
        MaterialRepository materialRepository = new MaterialRepository(assetManager);

        ITerrainQuadMeshStrategy[] meshStrategies = new ITerrainQuadMeshStrategy[6];
        meshStrategies[VoxelChunk.SIDE_Z_NEG] = new QuadMeshStrategyZAxis();
        meshStrategies[VoxelChunk.SIDE_Z_POS] = new QuadMeshStrategyZAxis();
        meshStrategies[VoxelChunk.SIDE_X_POS] = new QuadMeshStrategyXAxis();
        meshStrategies[VoxelChunk.SIDE_X_NEG] = new QuadMeshStrategyXAxis();
        meshStrategies[VoxelChunk.SIDE_Y_POS] = new QuadMeshStrategyYPos();
        meshStrategies[VoxelChunk.SIDE_Y_NEG] = new StandardQuadMeshStrategy();

        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository, meshStrategies);

        // TODO : Remove null, change collision system to use EventBus to avoid piping world manager everywhere
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, null);

        return new VoxelChunkHandler(voxelTypeLibrary, voxelChunkRebuilder);
    }
}
