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
    private boolean isHalf;

    public VoxelData(VoxelChunk chunk, Vec3i location, IVoxelType voxel, boolean isHalf) {
        this.chunk = chunk;
        this.location = location;
        this.voxel = voxel;
        this.isHalf = isHalf;
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

    public boolean isHalf() {
        return this.isHalf;
    }

    public Vector3f getLocation() {
        return this.location.asVector3f();
    }

    public Vec3i getGridLocation() {
        return this.location;
    }

    public void setType(IVoxelType voxelType) {
        this.chunk.set(location, voxelType.uniqueId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VoxelData voxelData = (VoxelData) o;

        if (!chunk.equals(voxelData.chunk)) return false;
        if (!location.equals(voxelData.location)) return false;
        return voxel.equals(voxelData.voxel);
    }

    @Override
    public int hashCode() {
        int result = chunk.hashCode();
        result = 31 * result + location.hashCode();
        result = 31 * result + voxel.hashCode();
        return result;
    }


}
