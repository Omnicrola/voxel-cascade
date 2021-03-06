package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by omnic on 3/5/2016.
 */
public class NavigationPath {

    private final ArrayList<Vector3f> navigationPoints;

    public NavigationPath(PathNode startNode) {
        this.navigationPoints = new ArrayList<>();
        addToPath(startNode);
        Collections.reverse(this.navigationPoints);
    }

    private void addToPath(PathNode pathNode) {
        if (pathNode != null) {
            float addY = pathNode.voxel.isHalf() ? 0.5f : 0f;
            Vector3f location = pathNode.voxel.getLocation().addLocal(0.5f, addY, 0.5f);
            this.navigationPoints.add(location);
            addToPath(pathNode.cameFrom);
        }
    }

    public boolean hasNext() {
        return this.navigationPoints.size() > 0;
    }

    public Vector3f next() {
        Vector3f remove = this.navigationPoints.remove(0);
        return remove;
    }
}
