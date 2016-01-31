package com.omnicrola.voxel.util;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by omnic on 1/24/2016.
 */
public class VoxelUtil {
    public static Stream<CollisionResult> convertToStream(CollisionResults collisionResults) {
        Iterable<CollisionResult> iterable = () -> collisionResults.iterator();
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static boolean booleanData(Spatial spatial, String dataKey) {
        Boolean userData = spatial.getUserData(dataKey);
        return userData != null && userData.booleanValue();
    }

    public static void printControls(Spatial spatial) {
        int numControls = spatial.getNumControls();
        for (int i = 0; i < numControls; i++) {
            Control control = spatial.getControl(i);
            System.out.println(control.getClass().getSimpleName());
        }
    }
}
