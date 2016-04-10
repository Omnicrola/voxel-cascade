package com.omnicrola.voxel.terrain;

import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.VoxelChunk;

import java.util.List;

/**
 * Created by Eric on 4/10/2016.
 */
public interface IChunkRebuildStrategy {
    void rebuild(List<VoxelChunk> chunksToRebuild, VoxelChunkRebuilder voxelChunkRebuilder);
    float percentComplete();
}
