package com.omnicrola.voxel.physics;

import com.jme3.collision.CollisionResult;

import java.util.Comparator;

/**
 * Created by omnic on 1/24/2016.
 */
public class CollisionDistanceComparator implements Comparator<CollisionResult> {
    @Override
    public int compare(CollisionResult result1, CollisionResult result2) {
        float d1 = result1.getDistance();
        float d2 = result2.getDistance();
        if (d1 > d2) {
            return 1;
        } else if (d2 > d1) {
            return -1;
        }
        return 0;
    }
}
