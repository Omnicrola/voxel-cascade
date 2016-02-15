package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/14/2016.
 */
public class TopOcclusionCalculator extends OcclusionCalculator {

    public TopOcclusionCalculator(VoxelChunkHandler voxelChunkHandler) {
        super(voxelChunkHandler);
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
        occlusionSet.set(0, vertex1(occluded));
        occlusionSet.set(1, vertex2(occluded));
        occlusionSet.set(2, vertex3(occluded));
        occlusionSet.set(3, vertex4(occluded));

        return occlusionSet;
    }

    private int vertex1(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[S]);
        occlusion += intValue(occluded[SW]);
        occlusion += intValue(occluded[W]);
        return occlusion;
    }

    private int vertex3(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[W]);
        occlusion += intValue(occluded[NW]);
        occlusion += intValue(occluded[N]);
        return occlusion;
    }

    private int vertex4(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[N]);
        occlusion += intValue(occluded[NE]);
        occlusion += intValue(occluded[E]);
        return occlusion;
    }

    private int vertex2(boolean[] occluded) {
        int occlusion = 0;
        occlusion += intValue(occluded[E]);
        occlusion += intValue(occluded[SE]);
        occlusion += intValue(occluded[S]);
        return occlusion;
    }

}
