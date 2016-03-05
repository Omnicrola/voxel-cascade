package com.omnicrola.voxel.terrain.data;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.terrain.IVoxelType;

/**
 * Created by omnic on 2/2/2016.
 */
public enum VoxelType implements IVoxelType {
    EMPTY((byte) 0, 1f, ColorRGBA.Black),
    BLUE((byte) 1, 1f, ColorRGBA.Blue),
    GREY((byte) 2, 1f, new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));

    private byte value;
    private float resources;
    private ColorRGBA color;

    VoxelType(byte value, float resources, ColorRGBA color) {
        this.value = value;
        this.resources = resources;
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

    @Override
    public float resourcesRequired() {
        return this.resources;
    }
}
