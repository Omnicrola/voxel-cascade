package com.omnicrola.voxel.world;

import com.omnicrola.voxel.engine.states.VoxelTerrainState;

/**
 * Created by Eric on 2/22/2016.
 */
public class CommandPackage {
    private final WorldEntityBuilder worldEntityBuilder;
    private final WorldEntityManager worldEntityManager;
    private final VoxelTerrainState voxelTerrainState;

    public CommandPackage(WorldEntityBuilder worldEntityBuilder, WorldEntityManager worldEntityManager, VoxelTerrainState voxelTerrainState) {
        this.worldEntityBuilder = worldEntityBuilder;
        this.worldEntityManager = worldEntityManager;
        this.voxelTerrainState = voxelTerrainState;
    }

    public WorldEntityBuilder getWorldEntityBuilder() {
        return worldEntityBuilder;
    }

    public WorldEntityManager getWorldEntityManager() {
        return worldEntityManager;
    }

    public VoxelTerrainState getVoxelTerrainState() {
        return voxelTerrainState;
    }
}
