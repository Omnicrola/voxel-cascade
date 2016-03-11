package com.omnicrola.voxel.terrain.data;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.terrain.IVoxelType;

/**
 * Created by omnic on 2/2/2016.
 */
public enum VoxelType implements IVoxelType {
    EMPTY((byte) 0, 1f, ColorRGBA.Black, false),
    BLUE((byte) 1, 1f, ColorRGBA.Blue),
    GREY((byte) 2, 1f, new ColorRGBA(0.5f, 0.5f, 0.5f, 1f));

    private final byte value;
    private final float resources;
    private final ColorRGBA color;
    private final boolean isSolid;

    VoxelType(byte value, float resources, ColorRGBA color, boolean isSolid) {
        this.value = value;
        this.resources = resources;
        this.color = color;
        this.isSolid = isSolid;
    }

    VoxelType(byte value, float resources, ColorRGBA color) {
        this(value, resources, color, true);
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

    @Override
    public boolean isSolid() {
        return this.isSolid;
    }
}
