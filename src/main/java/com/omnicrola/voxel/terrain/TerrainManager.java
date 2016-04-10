package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by Eric on 2/28/2016.
 */
public class TerrainManager implements ITerrainManager {

    private VoxelChunkHandler voxelChunkHandler;
    private VoxelTypeLibrary voxelTypeLibrary;

    public TerrainManager() {
        voxelTypeLibrary = new VoxelTypeLibrary();
    }

    @Override
    public void setVoxel(Vec3i location, byte voxelType) {
        this.voxelChunkHandler.set(location, voxelType);
    }

    @Override
    public VoxelData getVoxelAt(Vector3f location) {
        return this.voxelChunkHandler.getVoxelAt(Vec3i.floor(location));
    }

    @Override
    public VoxelData getVoxelAt(Vec3i location) {
        return this.voxelChunkHandler.getVoxelAt(location);
    }

    @Override
    public Optional<VoxelData> findLowestEmptyVoxel(Vector3f location) {
        return this.voxelChunkHandler.findLowestEmptyVoxel(location);
    }

    @Override
    public Optional<VoxelData> getHighestSolidVoxel(Vector3f location) {
        return this.voxelChunkHandler.findHighestSolidVoxel(location);
    }

    @Override
    public IVoxelType getVoxelType(byte voxelType) {
        return this.voxelTypeLibrary.lookup(voxelType);
    }

    public void globalRebuild() {
        this.voxelChunkHandler.flagAllChunksForRebuild();
    }

    public VoxelTypeLibrary getTypeLibrary() {
        return this.voxelTypeLibrary;
    }

    public void setCurrentHandler(VoxelChunkHandler currentHandler) {
        this.voxelChunkHandler = currentHandler;
    }

    public void setVoxelTypeLibrary(VoxelTypeLibrary voxelTypeLibrary) {
        this.voxelTypeLibrary = voxelTypeLibrary;
    }
}
