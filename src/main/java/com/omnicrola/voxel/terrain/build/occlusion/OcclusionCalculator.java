package com.omnicrola.voxel.terrain.build.occlusion;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/14/2016.
 */
public abstract class OcclusionCalculator implements IOcclusionCalculator {
    protected static final int SW = 0;
    protected static final int W = 1;
    protected static final int NW = 2;
    protected static final int S = 3;
    protected static final int N = 4;
    protected static final int SE = 5;
    protected static final int E = 6;
    protected static final int NE = 7;

    private VoxelChunkHandler voxelChunkHandler;

    protected OcclusionCalculator(VoxelChunkHandler voxelChunkHandler) {
        this.voxelChunkHandler = voxelChunkHandler;
    }


    protected int intValue(boolean occluded) {
        return occluded ? 1 : 0;
    }

    protected boolean isSolid(Vec3i globalLocation, int x, int y, int z) {
        return this.voxelChunkHandler.isVoxelSolidAt(globalLocation.translate(x, y, z));
    }
}
