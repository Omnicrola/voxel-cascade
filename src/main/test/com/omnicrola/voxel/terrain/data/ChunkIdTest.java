package com.omnicrola.voxel.terrain.data;

import com.omnicrola.util.Vec3i;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ChunkIdTest extends TestCase {

    @Test
    public void testFromGlobal_origin() throws Exception {
        ChunkId expectedId = new ChunkId(0, 0, 0);

        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(1, 1, 1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(15, 1, 1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(1, 15, 1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(1, 1, 15)));

        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 0, 0)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(0, 16, 0)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(0, 0, 16)));

        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, 0, 0)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(0, -1, 0)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(0, 0, -1)));
    }

    @Test
    public void testFromGlobal_positives() throws Exception {
        ChunkId expectedId = new ChunkId(1, 1, 1);

        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 16, 16)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(31, 16, 16)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 31, 16)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 16, 31)));

        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(32, 16, 16)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 32, 16)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 16, 32)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(15, 16, 16)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 15, 16)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(16, 16, 15)));
    }

    @Test
    public void testFromGlobal_negative() throws Exception {
        ChunkId expectedId = new ChunkId(-1, -1, -1);

        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -1, -1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-16, -1, -1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -16, -1)));
        assertEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -1, -16)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-17, -1, -1)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -17, -1)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -1, -17)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(0, -1, -1)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, 0, -1)));
        assertNotEquals(expectedId, ChunkId.fromGlobal(new Vec3i(-1, -1, 0)));
    }

    @Test
    public void testLocalize() throws Exception {
        ChunkId chunkId = new ChunkId(1, 3, 4);
        assertEquals(new Vec3i(4, 6, 9), chunkId.localize(new Vec3i(20, 54, 73)));
    }

    @Test
    public void testGlobalize() throws Exception {
        ChunkId chunkId = new ChunkId(2, 2, 2);

        assertEquals(new Vec3i(33, 33, 33), chunkId.globalize(1, 1, 1));
        assertEquals(new Vec3i(37, 39, 43), chunkId.globalize(5, 7, 11));
    }
}