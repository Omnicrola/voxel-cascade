package com.omnicrola.voxel.terrain;

/**
 * Created by Eric on 2/11/2016.
 */
public class VoxelTypeLibrary {

    private final IVoxelType[] types;

    public VoxelTypeLibrary() {
        this.types = new IVoxelType[255];
    }

    public void addType(IVoxelType voxelType) {
        this.types[voxelType.uniqueId()] = voxelType;
    }

    public IVoxelType lookup(byte voxel) {
        return this.types[voxel];
    }
}
