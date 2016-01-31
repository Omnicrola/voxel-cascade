package com.omnicrola.voxel.entities.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.input.SelectionGroup;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by omnic on 1/31/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class NavigationGridDistributorTest extends TestCase {

    @Mock
    private SelectionGroup mockSelectionGroup;

    @Test
    public void testDistribute_2units() throws Exception {
        when(mockSelectionGroup.count()).thenReturn(2);
        when(mockSelectionGroup.getLargestUnitSize()).thenReturn(1f);

        NavigationGridDistributor navigationGridDistributor = new NavigationGridDistributor(mockSelectionGroup);
        Iterator<Vector3f> distribute = navigationGridDistributor.distribute(new Vector3f());
        List<Vector3f> distributedPoints = drainIterator(distribute);
        assertEquals(4, distributedPoints.size());
        assertEquals(new Vector3f(-0.5f, 0, -0.5f), distributedPoints.get(0));
        assertEquals(new Vector3f(-0.5f, 0, 0.5f), distributedPoints.get(1));
        assertEquals(new Vector3f(-0.5f, 0, 0.5f), distributedPoints.get(1));
        assertEquals(new Vector3f(-0.5f, 0, 0.5f), distributedPoints.get(1));
    }

    @Test
    public void testDistribute_3Units(){
        when(mockSelectionGroup.count()).thenReturn(2);
        when(mockSelectionGroup.getLargestUnitSize()).thenReturn(1f);

        NavigationGridDistributor navigationGridDistributor = new NavigationGridDistributor(mockSelectionGroup);
        Iterator<Vector3f> distribute = navigationGridDistributor.distribute(new Vector3f());
        List<Vector3f> points = drainIterator(distribute);
        assertEquals(4, points.size());
    }

    @Test
    public void testDistribute_5units() throws Exception {
        when(mockSelectionGroup.count()).thenReturn(5);
        when(mockSelectionGroup.getLargestUnitSize()).thenReturn(1f);

        NavigationGridDistributor navigationGridDistributor = new NavigationGridDistributor(mockSelectionGroup);
        Iterator<Vector3f> distribute = navigationGridDistributor.distribute(new Vector3f());
        List<Vector3f> distributedPoints = drainIterator(distribute);
        assertEquals(9, distributedPoints.size());
        assertEquals(new Vector3f(-1f, 0, -1f), distributedPoints.get(0));
        assertEquals(new Vector3f(-1f, 0, 0f), distributedPoints.get(1));
        assertEquals(new Vector3f(-1f, 0, 1f), distributedPoints.get(2));
        assertEquals(new Vector3f(0f, 0, -1f), distributedPoints.get(3));
        assertEquals(new Vector3f(0f, 0, 0f), distributedPoints.get(4));
        assertEquals(new Vector3f(0f, 0, 1f), distributedPoints.get(5));
        assertEquals(new Vector3f(1f, 0, -1f), distributedPoints.get(6));
        assertEquals(new Vector3f(1f, 0, 0f), distributedPoints.get(7));
        assertEquals(new Vector3f(1f, 0, 1f), distributedPoints.get(8));
    }

    private static <T> List<T> drainIterator(Iterator<T> iterator) {
        ArrayList<T> copy = new ArrayList<>();
        while (iterator.hasNext()) {
            copy.add(iterator.next());
        }
        return copy;
    }
}