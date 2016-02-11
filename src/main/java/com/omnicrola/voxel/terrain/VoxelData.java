package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

/**
 * Created by Eric on 2/11/2016.
 */
public class VoxelData {
    private VoxelChunk chunk;
    private final Vec3i location;
    private final IVoxelType voxel;

    public VoxelData(VoxelChunk chunk, Vec3i location, IVoxelType voxel) {
        this.chunk = chunk;
        this.location = location;
        this.voxel = voxel;
    }

    public short getResources() {
        return this.chunk.getResourceGlobal(this.location);
    }

    public void setResources(short amount) {
        this.chunk.setResourceGlobal(this.location, amount);
    }
}
