package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.physics.VoxelPhysicsControl;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunk extends Node {

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
    }

    private final byte[][][] voxels;
    private final Geometry[][][] spatials;
    private ChunkId chunkId;
    private boolean isDirty;

    public VoxelChunk(ChunkId chunkId) {
        setName("Chunk " + chunkId);
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


    @Override
    public int attachChild(Spatial child) {
        throw new VoxelException("Cannot attach spatials directly to a terrain chunk");
    }

    @Override
    public int attachChildAt(Spatial child, int index) {
        throw new VoxelException("Cannot attach spatials directly to a terrain chunk");
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

    public void rebuild(Node parentNode, IGamePhysics physicsSpace) {
        parentNode.detachChild(this);
        physicsSpace.remove(this);
        super.detachAllChildren();

        for (int x = 0; x < this.spatials.length; x++) {
            for (int y = 0; y < this.spatials[x].length; y++) {
                for (int z = 0; z < this.spatials[x][y].length; z++) {
                    Spatial voxel = this.spatials[x][y][z];
                    if (voxel != null) {
                        super.attachChild(voxel);
                        Vector3f location = this.chunkId.globalize(x, y, z).asVector3f();
                        voxel.setLocalTranslation(location);
                        voxel.getControl(VoxelPhysicsControl.class).setPhysicsLocation(location);
                    }
                }
            }
        }
        parentNode.attachChild(this);
        physicsSpace.add(this);
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
