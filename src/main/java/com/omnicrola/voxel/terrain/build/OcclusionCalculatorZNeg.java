package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/14/2016.
 */
public class OcclusionCalculatorZNeg extends SideOcclusionCalculator {
    private static final int[][] zNegMatrix = matrix();

    private static int[][] matrix() {
        int[][] matrix = new int[3][];
        matrix[0] = new int[]{-1, -1, -1};
        matrix[1] = new int[]{0, -1, -1};
        matrix[2] = new int[]{1, -1, -1};
        return matrix;
    }

    public OcclusionCalculatorZNeg(VoxelChunkHandler voxelChunkHandler) {
        super(voxelChunkHandler, zNegMatrix);
    }


    @Override
    protected int occlude1(boolean[] occluded) {
        int totalOcclusion = 0;
        totalOcclusion += intValue(occluded[1]);
        totalOcclusion += intValue(occluded[2]);
        return totalOcclusion;
    }

    @Override
    protected int occlude2(boolean[] occluded) {
        return 0;
    }

    @Override
    protected int occlude3(boolean[] occluded) {
        int totalOcclusion = 0;
        totalOcclusion += intValue(occluded[0]);
        totalOcclusion += intValue(occluded[1]);
        return totalOcclusion;
    }

    @Override
    protected int occlude4(boolean[] occluded) {
        return 0;
    }
}
