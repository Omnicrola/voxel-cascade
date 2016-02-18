package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

/**
 * Created by Eric on 2/14/2016.
 */
public class OcclusionCalculatorBuilder {
    public static IOcclusionCalculator[] build(VoxelChunkHandler voxelChunkHandler) {
        IOcclusionCalculator[] calculators = new IOcclusionCalculator[6];
        calculators[VoxelChunk.SIDE_Z_NEG] = new OcclusionCalculatorZNeg(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_Z_POS] = new OcclusionCalculatorZPos(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_X_POS] = new OcclusionCalculatorXPos(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_X_NEG] = new OcclusionCalculatorXNeg(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_Y_POS] = new TopOcclusionCalculator(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_Y_NEG] = NullOcclusionCalculator.NO_OP;
        return calculators;
    }
}
