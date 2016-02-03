package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 2/2/2016.
 */
public class ChunkVertexLookup {

    public static final Vec3i[] INDEXES = new Vec3i[GameConstants.CHUNK_SIZE_CUBED];

    static {
        int index = 0;
        for (int x = 0; x < GameConstants.CHUNK_SIZE; x++) {
            for (int y = 0; y < GameConstants.CHUNK_SIZE; y++) {
                for (int z = 0; z < GameConstants.CHUNK_SIZE; z++) {
                    INDEXES[index] = new Vec3i(x, y, z);
                    index++;
                }
            }
        }
    }

    public static Vec3i findByIndex(int index) {
        if (index > INDEXES.length) {
            throw new VoxelException("Requested lookup index is beyond cubic chunk range of " + GameConstants.CHUNK_SIZE_CUBED);
        }
        return INDEXES[index];
    }
}
