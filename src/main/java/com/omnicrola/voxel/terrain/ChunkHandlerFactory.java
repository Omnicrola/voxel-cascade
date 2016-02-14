package com.omnicrola.voxel.terrain;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.build.TerrainQuadFactory;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;

/**
 * Created by Eric on 2/5/2016.
 */
public class ChunkHandlerFactory {
    private IGameContainer gameContainer;

    public ChunkHandlerFactory(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    public VoxelChunkHandler build(VoxelTypeLibrary voxelTypeLibrary) {
        AssetManager assetManager = this.gameContainer.getAssetManager();

        TerrainQuadFactory quadFactory = new TerrainQuadFactory(new MaterialRepository(assetManager));
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, this.gameContainer.physics(), this.gameContainer.world());
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(voxelTypeLibrary, voxelChunkRebuilder);
        return voxelChunkHandler;
    }
}
