package com.omnicrola.voxel.terrain.build.occlusion;

import com.omnicrola.voxel.terrain.VoxelChunkHandler;

/**
 * Created by Eric on 2/17/2016.
 */
public class OcclusionCalculatorXPos extends SideOcclusionCalculator {

    private static final int[][] xPosMatrix = matrix();

    private static int[][] matrix() {
        int[][] matrix = new int[5][];
        matrix[0] = new int[]{1, -1, -1};   // bottom right
        matrix[1] = new int[]{1, -1, 0};    // bottom
        matrix[2] = new int[]{1, -1, 1};    // bottom left
        matrix[3] = new int[]{1, 0, -1};    // right
        matrix[4] = new int[]{1, 0, 1};     // left
        return matrix;
    }

    public OcclusionCalculatorXPos(VoxelChunkHandler voxelChunkHandler) {
        super(voxelChunkHandler, xPosMatrix);
    }

    @Override
    protected int occlude1(boolean[] occluded) {
        // bottom right
        return occludeTrio(occluded, 0, 1, 3);
    }

    @Override
    protected int occlude2(boolean[] occluded) {
        // bottom left
        return occludeTrio(occluded, 1, 2, 4);
    }

    @Override
    protected int occlude3(boolean[] occluded) {
        // top right
        return intValue(occluded[3]);
    }

    @Override
    protected int occlude4(boolean[] occluded) {
        // top left;
        return intValue(occluded[4]);
    }
}
