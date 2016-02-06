package com.omnicrola.voxel.terrain;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by Eric on 2/5/2016.
 */
public class ChunkHandlerFactory {
    private IGameContainer gameContainer;

    public ChunkHandlerFactory(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    public VoxelChunkHandler build() {
        AssetManager assetManager = this.gameContainer.getAssetManager();
        QuadFactory quadFactory = new QuadFactory(new MaterialRepository(assetManager));
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, this.gameContainer.physics());
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(voxelChunkRebuilder);
        return voxelChunkHandler;
    }
}