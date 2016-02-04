package com.omnicrola.voxel.terrain;

/**
 * Created by Eric on 2/3/2016.
 */
public class VoxelFace {

    private final IVoxelType type;
    private final int side;
    private final boolean isTransparent;

    public VoxelFace(IVoxelType type, int side) {
        this.type = type;
        this.side = side;
        this.isTransparent = type == VoxelType.EMPTY;
    }

    public boolean isTransparent() {
        return this.isTransparent;
    }

    public IVoxelType type() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoxelFace)) return false;

        VoxelFace voxelFace = (VoxelFace) o;

        if (side != voxelFace.side) return false;
        if (isTransparent != voxelFace.isTransparent) return false;
        return !(type != null ? !type.equals(voxelFace.type) : voxelFace.type != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + side;
        result = 31 * result + (isTransparent ? 1 : 0);
        return result;
    }
}
