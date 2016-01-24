package com.omnicrola.voxel.entities.ai;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.input.SelectionGroup;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by omnic on 1/24/2016.
 */
public class NavigationGridDistributor {
    private SelectionGroup selectionGroup;

    public NavigationGridDistributor(SelectionGroup selectionGroup) {
        this.selectionGroup = selectionGroup;
    }

    // constructs a grid of location based on a starting point, allowing
    // units to go to the "same" location without trying to pile on top of each other
    public Iterator<Vector3f> distribute(Vector3f target) {
        int gridSize = (int) Math.ceil(FastMath.sqrt(this.selectionGroup.count()));
        float largestUnit = this.selectionGroup.getLargestUnitSize();
        float gridDimension = gridSize * largestUnit;
        float halfDimension = gridDimension / 2f;

        float startX = target.x - halfDimension;
        float startZ = target.z - halfDimension;
        ArrayList<Vector3f> gridPoints = new ArrayList<>(gridSize * gridSize);
        for (float x = startX; x <= startX + halfDimension; x += largestUnit) {
            for (float z = startZ; z <= startZ + halfDimension; z += largestUnit) {
                gridPoints.add(new Vector3f(x, target.y, z));
            }
        }
        return gridPoints.iterator();
    }
}
