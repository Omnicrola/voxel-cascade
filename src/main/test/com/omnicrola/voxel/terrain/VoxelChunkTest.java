package com.omnicrola.voxel.terrain;

import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.settings.GameConstants;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelChunkTest extends TestCase {
    @Test
    public void testChunkId() throws Exception {
        ChunkId chunkId = new ChunkId(randInt(15), randInt(15), randInt(15));
        VoxelChunk voxelChunk = new VoxelChunk(chunkId);

        assertEquals(chunkId, voxelChunk.getChunkId());
    }

    @Test
    public void testSettingVoxels_noOffset() throws Exception {
        VoxelChunk voxelChunk = new VoxelChunk(new ChunkId(0, 0, 0));

        int x = randInt(15);
        int y = randInt(15);
        int z = randInt(15);
        Geometry geometry1 = mockGeometry();
        voxelChunk.set(new Vec3i(x, y, z)).to(geometry1);
        assertSame(geometry1, voxelChunk.get(new Vec3i(x, y, z)));
    }

    @Test
    public void testSettingVoxels_withOffset() throws Exception {
        VoxelChunk voxelChunk = new VoxelChunk(new ChunkId(5, 2, 1));

        int x = randInt(15) + 5 * GameConstants.CHUNK_SIZE;
        int y = randInt(15) + 2 * GameConstants.CHUNK_SIZE;
        int z = randInt(15) + 1 * GameConstants.CHUNK_SIZE;
        Geometry geometry1 = mockGeometry();
        voxelChunk.set(new Vec3i(x, y, z)).to(geometry1);
        assertSame(geometry1, voxelChunk.get(new Vec3i(x, y, z)));
    }

    @Test
    public void testDirty() throws Exception {
        VoxelChunk voxelChunk = new VoxelChunk(new ChunkId(0, 0, 0));
        assertFalse(voxelChunk.needsRebuilt());

        voxelChunk.set(new Vec3i(1, 1, 1)).to(mockGeometry());
        assertTrue(voxelChunk.needsRebuilt());

        voxelChunk.clearRebuildFlag();
        assertFalse(voxelChunk.needsRebuilt());
    }

    private Geometry mockGeometry() {
        Geometry mock = mock(Geometry.class);
        return mock;
    }

    private int randInt(int max) {
        return (int) Math.random() * max;
    }
}