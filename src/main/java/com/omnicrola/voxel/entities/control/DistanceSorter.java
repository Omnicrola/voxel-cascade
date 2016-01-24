package com.omnicrola.voxel.entities.control;

import com.jme3.collision.CollisionResult;

import java.util.Comparator;

/**
 * Created by omnic on 1/23/2016.
 */
public class DistanceSorter implements Comparator<CollisionResult> {
    @Override
    public int compare(CollisionResult result1, CollisionResult result2) {
        float distance1 = result1.getDistance();
        float distance2 = result2.getDistance();
        if (distance1 > distance2) {
            return 1;
        } else if (distance2 > distance1) {
            return -1;
        }
        return 0;
    }
}
