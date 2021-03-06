package com.omnicrola.voxel.util;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.settings.EntityDataKeys;

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

    public static float floatData(Spatial spatial, String key) {
        Float value = spatial.getUserData(key);
        if (value == null) {
            return 0;
        } else {
            return value.floatValue();
        }
    }

    public static boolean isAlive(Spatial spatial) {
        if (spatial == null) {
            return false;
        }
        return floatData(spatial, EntityDataKeys.HITPOINTS) > 0f;
    }

    public static boolean isDead(Spatial spatial) {
        return !isAlive(spatial);
    }

    public static int integerData(Spatial spatial, String key) {
        Integer userData = spatial.getUserData(key);
        if (userData == null) {
            return 0;
        }
        return userData;
    }

    public static boolean belongsToTeam(Spatial spatial, TeamId teamToMatch) {
        TeamId spatialTeam = spatial.getUserData(EntityDataKeys.TEAM_ID);
        if (spatialTeam == null) {
            return false;
        }
        return teamToMatch.equals(spatialTeam);
    }
}
