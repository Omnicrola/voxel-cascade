package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/14/2016.
 */
public abstract class SideOcclusionCalculator extends OcclusionCalculator {
    private final VoxelChunkHandler voxelChunkHandler;
    private final int[][] om;

    public SideOcclusionCalculator(VoxelChunkHandler voxelChunkHandler, int[][] offsetMatrix) {
        super(voxelChunkHandler);
        this.voxelChunkHandler = voxelChunkHandler;
        this.om = offsetMatrix;
    }

    @Override
    public OcclusionSet calculate(Vec3i globalLocation) {
        boolean[] occluded = new boolean[5];
        occluded[0] = isSolid(globalLocation, om[0][0], om[0][1], om[0][2]);
        occluded[1] = isSolid(globalLocation, om[1][0], om[1][1], om[1][2]);
        occluded[2] = isSolid(globalLocation, om[2][0], om[2][1], om[2][2]);
        occluded[3] = isSolid(globalLocation, om[3][0], om[3][1], om[3][2]);
        occluded[4] = isSolid(globalLocation, om[4][0], om[4][1], om[4][2]);

        OcclusionSet occlusionSet = new OcclusionSet();
        occlusionSet.set(0, occlude1(occluded));
        occlusionSet.set(1, occlude2(occluded));
        occlusionSet.set(2, occlude3(occluded));
        occlusionSet.set(3, occlude4(occluded));

        return occlusionSet;
    }

    protected abstract int occlude1(boolean[] occluded);

    protected abstract int occlude2(boolean[] occluded);

    protected abstract int occlude3(boolean[] occluded);

    protected abstract int occlude4(boolean[] occluded);

    protected int occludePair(boolean[] occluded, int index1, int index2) {
        return intValue(occluded[index1]) + intValue(occluded[index2]);
    }

    protected int occludeTrio(boolean[] occluded, int index1, int index2, int index3) {
        return intValue(occluded[index1]) +
                intValue(occluded[index2]) +
                intValue(occluded[index3]);
    }


}
