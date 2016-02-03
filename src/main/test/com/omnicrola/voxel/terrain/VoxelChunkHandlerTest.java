package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.omnicrola.util.TestTools.assertIsOfType;
import static org.mockito.Mockito.mock;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelChunkHandlerTest extends TestCase {

    @Mock
    private VoxelChunkRebuilder mockChunkRebuilder;

    @Test
    public void testCreateCreatesAChunkWithTheCorrectId() throws Exception {
        Geometry mockGeometry = mockGeometry();

        VoxelChunkHandler voxelChunkHandler = new VoxelChunkHandler(mockChunkRebuilder, null);
        assertNull(voxelChunkHandler.getChunkContaining(new Vec3i(1, 1, 1)));

        voxelChunkHandler.create(mockGeometry, new Vec3i(2, 3, 5));
        VoxelChunk voxelChunk = assertIsOfType(VoxelChunk.class, voxelChunkHandler.getChunkContaining(new Vec3i(2, 3, 5)));
        assertEquals(ChunkId.fromGlobal(new Vec3i(2, 3, 5)), voxelChunk.getChunkId());
    }

    private Geometry mockGeometry() {
        Geometry mock = mock(Geometry.class);
        return mock;
    }
}