package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;

import java.util.BitSet;

/**
 * Created by Eric on 2/2/2016.
 */
public class VoxelFaceParser {
    private static final int CUBIC_CHUNK_SIZE = GameConstants.CHUNK_SIZE * GameConstants.CHUNK_SIZE * GameConstants.CHUNK_SIZE;


    public VoxelFaceParser() {
    }

    public BitSet parseFaces(Vec3i normalDirection, VoxelChunk chunk, VoxelChunkHandler voxelChunkHandler) {
        BitSet activeFaces = new BitSet(CUBIC_CHUNK_SIZE);
        ChunkIterator iterator = chunk.iterator(voxelChunkHandler);
        int index = 0;
        while (iterator.hasNext()) {
            iterator.advance();
            byte value = iterator.value();
            byte neighbor = iterator.neighborValue(normalDirection);
            if (isSolid(value) && !isSolid(neighbor)) {
                activeFaces.set(index, true);
            } else {
                activeFaces.set(index, false);
            }
        }
        return activeFaces;
    }

    private boolean isSolid(byte value) {
        return value != 0;
    }
}
