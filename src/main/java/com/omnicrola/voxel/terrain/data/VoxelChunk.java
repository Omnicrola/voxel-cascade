package com.omnicrola.voxel.terrain.data;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.TerrainAdapter;
import com.omnicrola.voxel.terrain.build.FaceBuilder;

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
    private final float[][][] resources;
    private ChunkId chunkId;
    private boolean isDirty;
    private FaceBuilder faceBuilder;
    private TerrainAdapter terrainAdapter;

    public VoxelChunk(ChunkId chunkId, FaceBuilder faceBuilder, TerrainAdapter terrainAdapter) {
        this.faceBuilder = faceBuilder;
        this.terrainAdapter = terrainAdapter;
        setName("Chunk ID:" + chunkId);
        this.chunkId = chunkId;
        int size = GameConstants.CHUNK_SIZE;
        this.voxels = new byte[size][size][size];
        this.resources = new float[size][size][size];
        this.isDirty = false;
    }

    public void set(Vec3i location, byte type) {
        Vec3i localize = this.chunkId.localize(location);
        setVoxel(localize, type);
    }

    private byte getVoxel(Vec3i index) {
        return this.voxels[index.getX()][index.getY()][index.getZ()];
    }

    private void setVoxel(Vec3i index, byte value) {
        this.voxels[index.getX()][index.getY()][index.getZ()] = value;
        this.isDirty = true;
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
        Vec3i global = this.chunkId.globalize(x, y, z);
        return this.faceBuilder.build(global, side);
    }


    @Override
    public void detachAllChildren() {
        this.getChildren().forEach(spatial -> terrainAdapter.removeQuad(spatial));
        super.detachAllChildren();
    }

    @Override
    public int attachChild(Spatial child) {
        this.terrainAdapter.attachQuad(child);
        return super.attachChild(child);
    }

    public float getResourceGlobal(Vec3i globalLocation) {
        Vec3i localize = this.chunkId.localize(globalLocation);
        float resources = this.resources[localize.getX()][localize.getY()][localize.getZ()];
        return resources;
    }

    public void setResourceGlobal(Vec3i globalLocation, float amount) {
        Vec3i localize = this.chunkId.localize(globalLocation);
        this.resources[localize.getX()][localize.getY()][localize.getZ()] = amount;
    }

    public void dispose() {
        Node parent = this.getParent();
        if (parent != null) {
            parent.detachChild(this);
        }
    }

}

