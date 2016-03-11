package com.omnicrola.voxel.terrain.data;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.IVoxelType;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by omnic on 3/5/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class VoxelDataTest extends TestCase {

    @Mock
    VoxelChunk mockVoxelChunk;
    @Mock
    Vec3i mockLocation;
    @Mock
    IVoxelType mockType;

    @Test
    public void testEqualsAndHash() throws Exception {
        VoxelData voxelData1 = new VoxelData(mockVoxelChunk, mockLocation, mockType, true);
        VoxelData voxelData2 = new VoxelData(mockVoxelChunk, mockLocation, mockType, true);

        assertEquals(voxelData1, voxelData1);
        assertEquals(voxelData1, voxelData2);
        assertEquals(voxelData2, voxelData1);

        assertEquals(voxelData1.hashCode(), voxelData1.hashCode());
        assertEquals(voxelData1.hashCode(), voxelData2.hashCode());
    }
}