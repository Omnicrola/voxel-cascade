package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Eric on 3/6/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class AStarFrontierTest {

    @Mock
    ITerrainManager mockTerrainManager;

    @Test
    public void testReturnsVoxelsSortedByDistance() throws Exception {
        Vector3f startLocation = new Vector3f(5, 5, 5);
        Vector3f endLocation = new Vector3f(0, 0, 0);

        VoxelData voxel1 = mockVoxel(new Vector3f(1, 0, 0));
        VoxelData voxel2 = mockVoxel(new Vector3f(2, 0, 0));
        VoxelData voxel3 = mockVoxel(new Vector3f(3, 0, 0));
        VoxelData voxel4 = mockVoxel(new Vector3f(4, 0, 0));
        VoxelData voxel5 = mockVoxel(new Vector3f(5, 0, 0));

        when(mockTerrainManager.getVoxelAt(startLocation)).thenReturn(voxel1);
        List<VoxelData> neighbors = Arrays.asList(voxel2, voxel3, voxel4, voxel5);
        Collections.shuffle(neighbors);
        when(mockTerrainManager.getNeighborsOf(voxel1)).thenReturn(neighbors);

        AStarFrontier frontier = new AStarFrontier(mockTerrainManager, startLocation, endLocation);

        assertTrue(frontier.hasNext());
        System.out.println(frontier.next());
        System.out.println(frontier.next());
        System.out.println(frontier.next());
        System.out.println(frontier.next());

        assertSame(voxel2, frontier.next());
        assertSame(voxel3, frontier.next());
        assertSame(voxel4, frontier.next());
        assertSame(voxel5, frontier.next());

        assertFalse(frontier.hasNext());
    }

    private VoxelData mockVoxel(Vector3f location) {
        VoxelData voxel = mock(VoxelData.class);
        when(voxel.getLocation()).thenReturn(location);
        when(voxel.toString()).thenReturn(location.toString());
        return voxel;
    }
}