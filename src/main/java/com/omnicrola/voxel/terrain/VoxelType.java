package com.omnicrola.voxel.terrain;

/**
 * Created by omnic on 2/2/2016.
 */
public enum VoxelType implements IVoxelType {
    SOLID((byte) 1);

    private byte value;

    VoxelType(byte value) {
        this.value = value;
    }

    @Override
    public byte uniqueId() {
        return this.value;
    }
}
