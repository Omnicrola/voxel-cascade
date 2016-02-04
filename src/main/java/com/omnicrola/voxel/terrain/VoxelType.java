package com.omnicrola.voxel.terrain;

/**
 * Created by omnic on 2/2/2016.
 */
public enum VoxelType implements IVoxelType {
    EMPTY((byte) 0, "ghost.png"),
    BLUE((byte) 1, "voxel-face-blue.png");

    private byte value;
    private String texture;

    VoxelType(byte value, String texture) {
        this.value = value;
        this.texture = texture;
    }

    @Override
    public byte uniqueId() {
        return this.value;
    }

    @Override
    public String texture() {
        return this.texture;
    }
}
