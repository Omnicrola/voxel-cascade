package com.omnicrola.voxel.terrain;

import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Arrays;

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
        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();


        QuadFactory quadFactory = new QuadFactory(new MaterialRepository(assetManager));
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, this.gameContainer.physics(), this.gameContainer.world());
        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(voxelTypeLibrary, voxelChunkRebuilder);
        return voxelChunkHandler;
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }
}
