package com.omnicrola.voxel.terrain.data;

import com.omnicrola.voxel.terrain.IVoxelType;

import java.util.BitSet;

/**
 * Created by Eric on 2/3/2016.
 */
public class VoxelFace {

    private final IVoxelType type;
    private final int side;
    private BitSet occlusionSet;
    private final boolean isTransparent;

    public VoxelFace(IVoxelType type, int side, BitSet occlusionSet) {
        this.type = type;
        this.side = side;
        this.occlusionSet = occlusionSet;
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
        if (type != null ? !type.equals(voxelFace.type) : voxelFace.type != null) return false;
        return !(occlusionSet != null ? !occlusionSet.equals(voxelFace.occlusionSet) : voxelFace.occlusionSet != null);

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + side;
        result = 31 * result + (occlusionSet != null ? occlusionSet.hashCode() : 0);
        result = 31 * result + (isTransparent ? 1 : 0);
        return result;
    }
}
