package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;

/**
 * Created by Eric on 1/31/2016.
 */
public class VoxelChunkRebuilder {


    private static final Vec3i TOP = new Vec3i(0,1,0);
    private static final byte NO_FACE = 0;
    private static final byte PRIMARY_FACE = 1;
    private static final byte SECONDARY_FACE = 2;

    private VoxelFaceParser voxelFaceParser;

    public VoxelChunkRebuilder(VoxelFaceParser voxelFaceParser) {
        this.voxelFaceParser = voxelFaceParser;
    }

    public void rebuild(VoxelChunk chunk, VoxelChunkHandler voxelChunkHandler) {
        this.voxelFaceParser.parseFaces(TOP, chunk, voxelChunkHandler);
    }

}
