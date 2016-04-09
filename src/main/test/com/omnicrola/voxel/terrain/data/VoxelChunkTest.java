package com.omnicrola.voxel.terrain.data;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.TerrainAdapter;
import com.omnicrola.voxel.terrain.build.FaceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Eric on 3/12/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelChunkTest {
    @Mock
    private FaceBuilder mockFaceBuilder;
    @Mock
    private TerrainAdapter mockTerrainAdapter;

    @Test
    public void testHalfFlags() throws Exception {
        VoxelChunk voxelChunk = new VoxelChunk(new ChunkId(0, 0, 0), mockFaceBuilder);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    Vec3i location = new Vec3i(x, y, z);
                    assertFalse("Location " + location + " should be FALSE, but was TRUE", voxelChunk.isHalfGlobal(location));
                    voxelChunk.setHalfGlobal(location, true);
                    assertTrue("Location " + location + " should be TRUE, but was FALSE", voxelChunk.isHalfGlobal(location));
                }
            }
        }
    }


}