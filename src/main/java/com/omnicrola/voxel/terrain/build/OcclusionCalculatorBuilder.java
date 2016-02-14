package com.omnicrola.voxel.terrain.build;

import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

/**
 * Created by Eric on 2/14/2016.
 */
public class OcclusionCalculatorBuilder {
    public static IOcclusionCalculator[] build(VoxelChunkHandler voxelChunkHandler) {
        IOcclusionCalculator[] calculators = new IOcclusionCalculator[6];
        calculators[VoxelChunk.SIDE_Z_NEG] = NullOcclusionCalculator.NO_OP;
        calculators[VoxelChunk.SIDE_Z_POS] = NullOcclusionCalculator.NO_OP;
        calculators[VoxelChunk.SIDE_X_POS] = NullOcclusionCalculator.NO_OP;
        calculators[VoxelChunk.SIDE_X_NEG] = NullOcclusionCalculator.NO_OP;
        calculators[VoxelChunk.SIDE_Y_POS] = new TopOcclusionCalculator(voxelChunkHandler);
        calculators[VoxelChunk.SIDE_Y_NEG] = NullOcclusionCalculator.NO_OP;
        return calculators;
    }
}
