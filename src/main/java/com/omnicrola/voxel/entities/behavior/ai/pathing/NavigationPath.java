package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;

import java.util.List;

/**
 * Created by omnic on 3/5/2016.
 */
public class NavigationPath {

    private List<Vector3f> pathNodes;

    public NavigationPath(List<Vector3f> pathNodes) {
        this.pathNodes = pathNodes;
    }

    public Vector3f next() {
        return this.pathNodes.remove(this.pathNodes.size());
    }
}
