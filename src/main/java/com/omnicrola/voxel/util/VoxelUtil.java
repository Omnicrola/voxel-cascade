package com.omnicrola.voxel.util;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;

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
}
