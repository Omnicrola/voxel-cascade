package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunk extends Geometry {

    private class ChunkSetter implements IChunkSetter {
        private Vec3i localize;

        public ChunkSetter(Vec3i localize) {
            this.localize = localize;
        }

        public void to(Geometry geometry) {
            if (getVoxel(this.localize) == 0) {
                setVoxel(this.localize, (byte) 1);
                setSpatial(this.localize, geometry);
                isDirty = true;
            }
        }

        @Override
        public void to(IVoxelType voxelType) {
            byte voxelValue = voxelType.uniqueId();
            if (getVoxel(this.localize) != voxelValue) {
                setVoxel(this.localize, voxelValue);
                isDirty = true;
            }
        }
    }

    private final byte[][][] voxels;
    private final Geometry[][][] spatials;
    private ChunkId chunkId;
    private boolean isDirty;

    public VoxelChunk(ChunkId chunkId) {
        setName("Chunk ID:" + chunkId);
        this.chunkId = chunkId;
        this.voxels = new byte[GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE];
        this.spatials = new Geometry[GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE];
        this.isDirty = false;
    }

    public Geometry get(Vec3i location) {
        Vec3i localize = this.chunkId.localize(location);
        return this.spatials[localize.getX()][localize.getY()][localize.getZ()];
    }

    public IChunkSetter set(Vec3i location) {
        Vec3i localize = this.chunkId.localize(location);
        return new ChunkSetter(localize);
    }

    private byte getVoxel(Vec3i index) {
        return this.voxels[index.getX()][index.getY()][index.getZ()];
    }

    private void setVoxel(Vec3i index, byte value) {
        this.voxels[index.getX()][index.getY()][index.getZ()] = value;
    }

    private void setSpatial(Vec3i index, Geometry geometry) {
        this.spatials[index.getX()][index.getY()][index.getZ()] = geometry;
    }

    public boolean needsRebuilt() {
        return this.isDirty;
    }

    public void flagForRebuild() {
        this.isDirty = true;
    }

    public void clearRebuildFlag() {
        this.isDirty = false;
    }

    public ChunkId getChunkId() {
        return this.chunkId;
    }

    public ChunkIterator iterator(VoxelChunkHandler voxelChunkHandler) {
        return new ChunkIterator(voxelChunkHandler, this);
    }

    public byte getVoxelLocal(int x, int y, int z) {
        return this.voxels[x][y][z];
    }

    public byte getVoxelGlobal(Vec3i global) {
        Vec3i localize = this.chunkId.localize(global);
        return getVoxel(localize);
    }
}

