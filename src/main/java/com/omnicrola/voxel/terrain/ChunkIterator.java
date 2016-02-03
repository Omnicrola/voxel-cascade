package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 1/31/2016.
 */
public class ChunkIterator {
    private int y;
    private int z;
    private int x;
    private VoxelChunkHandler chunkHandler;
    private VoxelChunk voxelChunk;

    public ChunkIterator(VoxelChunkHandler chunkHandler, VoxelChunk voxelChunk) {
        this.chunkHandler = chunkHandler;
        this.voxelChunk = voxelChunk;
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public boolean hasNext() {
        return this.x < GameConstants.CHUNK_SIZE - 1;
    }

    public byte value() {
        return this.voxelChunk.getVoxelLocal(x, y, z);
    }

    public byte neighborValue(Vec3i normalDirection) {
        int nX = normalDirection.getX() + this.x;
        int nY = normalDirection.getY() + this.y;
        int nZ = normalDirection.getZ() + this.z;
        if (isInBounds(nX, nY, nZ)) {
            return this.voxelChunk.getVoxelLocal(nX, nY, nZ);
        } else {
            Vec3i global = this.voxelChunk.getChunkId().globalize(nX, nY, nZ);
            return chunkHandler.getChunkContaining(global).getVoxelGlobal(global);
        }
    }

    private boolean isInBounds(int nX, int nY, int nZ) {
        int chunkSize = GameConstants.CHUNK_SIZE;
        return nX < chunkSize &&
                nY < chunkSize &&
                nZ < chunkSize;
    }

    public void advance() {
        this.z++;
        if (this.z >= GameConstants.CHUNK_SIZE) {
            this.z = 0;
            this.y++;
            if (this.y >= GameConstants.CHUNK_SIZE) {
                this.y = 0;
                this.x++;
            }
        }
    }
}
