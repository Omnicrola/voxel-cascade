package com.omnicrola.voxel.terrain.build;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.IVoxelType;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.build.occlusion.IOcclusionCalculator;
import com.omnicrola.voxel.terrain.build.occlusion.OcclusionSet;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.terrain.data.VoxelFace;

/**
 * Created by Eric on 2/13/2016.
 */
public class FaceBuilder {

    private VoxelChunkHandler voxelChunkHandler;
    private final IOcclusionCalculator[] occlusionCalculators;

    public FaceBuilder(VoxelChunkHandler voxelChunkHandler,
                       IOcclusionCalculator[] occlusionCalculators) {
        this.voxelChunkHandler = voxelChunkHandler;
        this.occlusionCalculators = occlusionCalculators;
    }

    public VoxelFace build(Vec3i globalLocation, int side) {
        IOcclusionCalculator occlusionCalculator = this.occlusionCalculators[side];
        OcclusionSet occlusionSet = occlusionCalculator.calculate(globalLocation);
        VoxelData voxelAt = this.voxelChunkHandler.getVoxelAt(globalLocation);
        IVoxelType type = voxelAt.getType();
        boolean isHalf = voxelAt.isHalf();
        VoxelFace voxelFace = new VoxelFace(type, side, occlusionSet, isHalf);
        return voxelFace;
    }

}
