package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;

import java.util.*;

/**
 * Created by omnic on 1/24/2016.
 */
public class NavigationGridDistributor {

    // constructs a grid of location based on a starting point, allowing
    // units to go to the "same" location without trying to pile on top of each other
    public Iterator<Vector3f> distribute(Vector3f target, List<Spatial> selectedUnits) {
        int gridSize = (int) Math.ceil(FastMath.sqrt(selectedUnits.size()));
        float largestUnitSize = getLargestUnitSize(selectedUnits);
        if (largestUnitSize <= 0) {
            return Arrays.asList(target).iterator();
        }
        float gridDimension = gridSize * largestUnitSize;
        float halfOfGrid = gridDimension / 2f;

        float halfUnitSize = largestUnitSize / 2f;
        float startX = target.x - halfOfGrid + halfUnitSize;
        float startZ = target.z - halfOfGrid + halfUnitSize;
        ArrayList<Vector3f> gridPoints = new ArrayList<>(gridSize * gridSize);
        float maxX = startX + gridDimension + halfUnitSize;
        float maxZ = startZ + gridDimension + halfUnitSize;

        for (float x = startX; x <= maxX; x += largestUnitSize) {
            for (float z = startZ; z <= maxZ; z += largestUnitSize) {
                gridPoints.add(new Vector3f(x, target.y, z));
            }
        }
        return gridPoints.iterator();
    }

    private float getLargestUnitSize(List<Spatial> selectedUnits) {
        Optional<Float> largest = selectedUnits
                .stream()
                .map(u -> getPersonalRadius(u))
                .sorted()
                .findFirst();
        if (largest.isPresent()) {
            return largest.get();
        } else {
            return 1.0f;
        }
    }

    private float getPersonalRadius(Spatial spatial) {
        EntityMotionControl motionControl = spatial.getControl(EntityMotionControl.class);
        if (motionControl == null) {
            return 0;
        } else {
            return motionControl.getPersonalRadius();
        }
    }

}
