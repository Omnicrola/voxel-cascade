package com.omnicrola.voxel.terrain.data;

import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.IVoxelType;

import java.util.Arrays;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunk extends Node {
    public static final int SIDE_Z_NEG = 0;
    public static final int SIDE_Z_POS = 1;
    public static final int SIDE_X_POS = 2;
    public static final int SIDE_X_NEG = 3;
    public static final int SIDE_Y_POS = 4;
    public static final int SIDE_Y_NEG = 5;


    private final byte[][][] voxels;
    private ChunkId chunkId;
    private boolean isDirty;

    public VoxelChunk(ChunkId chunkId) {
        setName("Chunk ID:" + chunkId);
        this.chunkId = chunkId;
        this.voxels = new byte[GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE][GameConstants.CHUNK_SIZE];
        this.isDirty = false;
    }

    public void set(Vec3i location, IVoxelType type) {
        Vec3i localize = this.chunkId.localize(location);
        setVoxel(localize, type.uniqueId());
    }

    private byte getVoxel(Vec3i index) {
        return this.voxels[index.getX()][index.getY()][index.getZ()];
    }

    private void setVoxel(Vec3i index, byte value) {
        this.voxels[index.getX()][index.getY()][index.getZ()] = value;
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

    public byte getVoxelLocal(int x, int y, int z) {
        return this.voxels[x][y][z];
    }

    public byte getVoxelGlobal(Vec3i global) {
        Vec3i localize = this.chunkId.localize(global);
        return getVoxel(localize);
    }

    public VoxelFace getVoxelFace(int x, int y, int z, int side) {
        return new VoxelFace(getType(this.voxels[x][y][z]), side);
    }

    private IVoxelType getType(byte type) {
        return Arrays.asList(VoxelType.values()).stream().filter(t -> t.uniqueId() == type).findFirst().get();
    }
}

