package com.omnicrola.voxel.terrain.data;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.IVoxelType;

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

    public float getResources() {
        return this.chunk.getResourceGlobal(this.location);
    }

    public void setResources(float amount) {
        this.chunk.setResourceGlobal(this.location, amount);
    }

    public void removeVoxel() {
        this.chunk.set(location, VoxelType.EMPTY.uniqueId());
    }

    public IVoxelType getType() {
        return voxel;
    }

    public Vector3f getLocation() {
        return this.location.asVector3f();
    }

    public void setType(IVoxelType voxelType) {
        this.chunk.set(location, voxelType.uniqueId());
    }
}
