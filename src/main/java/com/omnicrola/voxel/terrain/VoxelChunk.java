package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.physics.VoxelPhysicsControl;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelChunk extends Node {

    private class ChunkSetter implements IChunkSetter {

        private final int x;
        private final int y;
        private final int z;

        public ChunkSetter(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public void to(Geometry geometry) {
            voxels[x][y][z] = (byte) 1;
            spatials[x][y][z] = geometry;
            isDirty = true;
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

    public Geometry get(int x, int y, int z) {
        x = this.chunkId.localizeX(x);
        y = this.chunkId.localizeY(y);
        z = this.chunkId.localizeZ(z);
        return this.spatials[x][y][z];
    }

    public IChunkSetter set(int x, int y, int z) {
        x = this.chunkId.localizeX(x);
        y = this.chunkId.localizeY(y);
        z = this.chunkId.localizeZ(z);
        return new ChunkSetter(x, y, z);
    }



    @Override
    public int attachChild(Spatial child) {
        throw new VoxelException("Cannot attach spatials directly to a terrain chunk");
    }

    @Override
    public int attachChildAt(Spatial child, int index) {
        throw new VoxelException("Cannot attach spatials directly to a terrain chunk");
    }

    @Override
    public void updateLogicalState(float tpf) {

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
                        Vector3f location = this.chunkId.globalize(x,y,z);
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
}
