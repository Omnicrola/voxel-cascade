package com.omnicrola.voxel.terrain.data;

import com.omnicrola.voxel.terrain.IVoxelType;
import com.omnicrola.voxel.terrain.build.OcclusionSet;

/**
 * Created by Eric on 2/3/2016.
 */
public class VoxelFace {

    private final IVoxelType type;
    private final int side;
    private final OcclusionSet occlusionSet;
    private final boolean isTransparent;

    public VoxelFace(IVoxelType type, int side, OcclusionSet occlusionSet) {
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
        if (!type.equals(voxelFace.type)) return false;
        return occlusionSet.equals(voxelFace.occlusionSet);

    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + side;
        result = 31 * result + occlusionSet.hashCode();
        result = 31 * result + (isTransparent ? 1 : 0);
        return result;
    }

    public OcclusionSet getOcclusion() {
        return this.occlusionSet;
    }

}
