package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;

/**
 * Created by omnic on 2/2/2016.
 */
public class EmptyVoxelChunk extends VoxelChunk {

    public static final EmptyVoxelChunk NO_OP = new EmptyVoxelChunk();

    private EmptyVoxelChunk() {
        super(new ChunkId(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE));
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
    public IChunkSetter set(Vec3i location) {
        return new IChunkSetter() {
            @Override
            public void to(Geometry geometry) {
            }

            @Override
            public void to(IVoxelType voxelType) {

            }
        };
    }
}
