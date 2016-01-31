package com.omnicrola.voxel.terrain;

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

        assertEquals(expectedId, ChunkId.fromGlobal(1, 1, 1));
        assertEquals(expectedId, ChunkId.fromGlobal(15, 1, 1));
        assertEquals(expectedId, ChunkId.fromGlobal(1, 15, 1));
        assertEquals(expectedId, ChunkId.fromGlobal(1, 1, 15));

        assertNotEquals(expectedId, ChunkId.fromGlobal(16, 0, 0));
        assertNotEquals(expectedId, ChunkId.fromGlobal(0, 16, 0));
        assertNotEquals(expectedId, ChunkId.fromGlobal(0, 0, 16));

        assertNotEquals(expectedId, ChunkId.fromGlobal(-1, 0, 0));
        assertNotEquals(expectedId, ChunkId.fromGlobal(0, -1, 0));
        assertNotEquals(expectedId, ChunkId.fromGlobal(0, 0, -1));
    }

    @Test
    public void testFromGlobal_positives() throws Exception {
        ChunkId expectedId = new ChunkId(1, 1, 1);

        assertEquals(expectedId, ChunkId.fromGlobal(16, 16, 16));
        assertEquals(expectedId, ChunkId.fromGlobal(31, 16, 16));
        assertEquals(expectedId, ChunkId.fromGlobal(16, 31, 16));
        assertEquals(expectedId, ChunkId.fromGlobal(16, 16, 31));

        assertNotEquals(expectedId, ChunkId.fromGlobal(32, 16, 16));
        assertNotEquals(expectedId, ChunkId.fromGlobal(16, 32, 16));
        assertNotEquals(expectedId, ChunkId.fromGlobal(16, 16, 32));

        assertNotEquals(expectedId, ChunkId.fromGlobal(15, 16, 16));
        assertNotEquals(expectedId, ChunkId.fromGlobal(16, 15, 16));
        assertNotEquals(expectedId, ChunkId.fromGlobal(16, 16, 15));
    }

    @Test
    public void testFromGlobal_negative() throws Exception {
        ChunkId expectedId = new ChunkId(-1, -1, -1);

        assertEquals(expectedId, ChunkId.fromGlobal(-1, -1, -1));
        assertEquals(expectedId, ChunkId.fromGlobal(-16, -1, -1));
        assertEquals(expectedId, ChunkId.fromGlobal(-1, -16, -1));
        assertEquals(expectedId, ChunkId.fromGlobal(-1, -1, -16));

        assertNotEquals(expectedId, ChunkId.fromGlobal(-17, -1, -1));
        assertNotEquals(expectedId, ChunkId.fromGlobal(-1, -17, -1));
        assertNotEquals(expectedId, ChunkId.fromGlobal(-1, -1, -17));

        assertNotEquals(expectedId, ChunkId.fromGlobal(0, -1, -1));
        assertNotEquals(expectedId, ChunkId.fromGlobal(-1, 0, -1));
        assertNotEquals(expectedId, ChunkId.fromGlobal(-1, -1, 0));
    }
}