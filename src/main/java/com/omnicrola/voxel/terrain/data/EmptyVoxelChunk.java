package com.omnicrola.voxel.terrain.data;

import com.omnicrola.util.Vec3i;

/**
 * Created by omnic on 2/2/2016.
 */
public class EmptyVoxelChunk extends VoxelChunk {

    public static final EmptyVoxelChunk NO_OP = new EmptyVoxelChunk();

    private EmptyVoxelChunk() {
        super(new ChunkId(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE), null, null);
    }

    @Override
    public byte getVoxelGlobal(Vec3i global) {
        return 0;
    }

    @Override
    public byte getVoxelLocal(int x, int y, int z) {
        return 0;
    }

    @Override
    public void set(Vec3i location, byte type) {
    }

    @Override
    public VoxelFace getVoxelFace(int x, int y, int z, int side) {
        return new VoxelFace(VoxelType.EMPTY, side, null, false);
    }
}
