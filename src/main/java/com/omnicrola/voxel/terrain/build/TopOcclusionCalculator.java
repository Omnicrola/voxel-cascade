package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/14/2016.
 */
public class TopOcclusionCalculator implements IOcclusionCalculator {
    private static final int SW = 0;
    private static final int W = 1;
    private static final int NW = 2;
    private static final int S = 3;
    private static final int N = 4;
    private static final int SE = 5;
    private static final int E = 6;
    private static final int NE = 7;

    private VoxelChunkHandler voxelChunkHandler;

    public TopOcclusionCalculator(VoxelChunkHandler voxelChunkHandler) {
        this.voxelChunkHandler = voxelChunkHandler;
    }

    @Override
    public OcclusionSet calculate(Vec3i globalLocation) {
        boolean[] occluded = new boolean[8];
        occluded[SW] = isSolid(globalLocation, -1, 1, -1);   // SW
        occluded[W] = isSolid(globalLocation, -1, 1, 0);    // W
        occluded[NW] = isSolid(globalLocation, -1, 1, 1);    // NW

        occluded[S] = isSolid(globalLocation, 0, 1, -1);    // S
        occluded[N] = isSolid(globalLocation, 0, 1, 1);     // N

        occluded[SE] = isSolid(globalLocation, 1, 1, -1);    // SE
        occluded[E] = isSolid(globalLocation, 1, 1, 0);     // E
        occluded[NE] = isSolid(globalLocation, 1, 1, 1);     // NE

        OcclusionSet occlusionSet = new OcclusionSet();
        occlusionSet.set(0, vertexOne(occluded));
        occlusionSet.set(1, vertexFour(occluded));
        occlusionSet.set(2, vertexTwo(occluded));
        occlusionSet.set(3, vertexThree(occluded));

        return occlusionSet;
    }

    private int vertexOne(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[S]);
        occlusion += intValue(occluded[SW]);
        occlusion += intValue(occluded[W]);
        return occlusion;
    }

    private int vertexTwo(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[W]);
        occlusion += intValue(occluded[NW]);
        occlusion += intValue(occluded[N]);
        return occlusion;
    }

    private int vertexThree(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[N]);
        occlusion += intValue(occluded[NE]);
        occlusion += intValue(occluded[E]);
        return occlusion;
    }

    private int vertexFour(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[E]);
        occlusion += intValue(occluded[SE]);
        occlusion += intValue(occluded[S]);
        return occlusion;
    }

    private int intValue(boolean occluded) {
        return occluded ? 1 : 0;
    }

    private boolean isSolid(Vec3i globalLocation, int x, int y, int z) {
        return this.voxelChunkHandler.isVoxelSolidAt(globalLocation.translate(x, y, z));
    }
}
