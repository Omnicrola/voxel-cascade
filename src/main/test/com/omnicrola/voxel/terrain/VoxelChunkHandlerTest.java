package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static com.omnicrola.util.TestTools.assertIsOfType;
import static org.mockito.Mockito.mock;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelChunkHandlerTest extends TestCase {

    @Test
    public void testCreateCreatesAChunkWithTheCorrectId() throws Exception {
        Geometry mockGeometry = mockGeometry();

        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler();
        assertNull(voxelChunkHandler.getChunkContaining(1, 1, 1));

        voxelChunkHandler.create(mockGeometry, 2, 3, 5);
        VoxelChunk voxelChunk = assertIsOfType(VoxelChunk.class, voxelChunkHandler.getChunkContaining(2, 3, 5));
        assertEquals(ChunkId.fromGlobal(2, 3, 5), voxelChunk.getChunkId());
    }

    private Geometry mockGeometry() {
        Geometry mock = mock(Geometry.class);
        return mock;
    }
}