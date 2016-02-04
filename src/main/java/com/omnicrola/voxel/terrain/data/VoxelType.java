package com.omnicrola.voxel.terrain.data;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.terrain.IVoxelType;

/**
 * Created by omnic on 2/2/2016.
 */
public enum VoxelType implements IVoxelType {
    EMPTY((byte) 0, ColorRGBA.Black),
    BLUE((byte) 1, ColorRGBA.Blue),
    GREY((byte) 2, new ColorRGBA(0.7f, 0.7f, 0.7f, 1f));

    private byte value;
    private ColorRGBA color;

    VoxelType(byte value, ColorRGBA color) {
        this.value = value;
        this.color = color;
    }

    @Override
    public byte uniqueId() {
        return this.value;
    }

    @Override
    public ColorRGBA color() {
        return this.color;
    }
}
