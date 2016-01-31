package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
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

    @Test
    public void testLocalize() throws Exception {
        ChunkId chunkId = new ChunkId(1, 3, 4);
        assertEquals(4, chunkId.localizeX(20));
        assertEquals(38, chunkId.localizeX(54));
        assertEquals(57, chunkId.localizeX(73));
    }

    @Test
    public void testGlobalize() throws Exception {
        ChunkId chunkId = new ChunkId(2, 2, 2);

        assertEquals(new Vector3f(33, 33, 33), chunkId.globalize(1, 1, 1));
        assertEquals(new Vector3f(37, 39, 43), chunkId.globalize(5, 7, 11));
    }
}