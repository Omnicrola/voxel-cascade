package com.omnicrola.voxel.terrain;

import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/31/2016.
 */
public class ChunkId {

    public static ChunkId fromGlobal(int x, int y, int z) {
        x = alignToGrid(x);
        y = alignToGrid(y);
        z = alignToGrid(z);
        return new ChunkId(x, y, z);
    }

    private static int alignToGrid(float x) {
        return (int) Math.floor(x / (float) GameConstants.CHUNK_SIZE);
    }

    private final int x;
    private final int y;
    private final int z;

    ChunkId(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChunkId chunkId = (ChunkId) o;

        if (x != chunkId.x) return false;
        if (y != chunkId.y) return false;
        return z == chunkId.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return "ChunkId{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
