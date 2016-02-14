package com.omnicrola.voxel.terrain.data;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelChunkTest extends TestCase {
    @Test
    public void testChunkId() throws Exception {
        ChunkId chunkId = new ChunkId(randInt(15), randInt(15), randInt(15));
        VoxelChunk voxelChunk = new VoxelChunk(chunkId, null);

        assertEquals(chunkId, voxelChunk.getChunkId());
    }

    private int randInt(int max) {
        return (int) Math.random() * max;
    }
}