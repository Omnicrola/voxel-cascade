package com.omnicrola.voxel.entities.control.collision;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/17/2016.
 */
public class ProjectileCollisionHandler extends AbstractCollisionHandler {

    public ProjectileCollisionHandler(Spatial projectile, WorldManager worldManager) {
        super(projectile, worldManager);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            doNothing();
        } else if (isTerrain(otherObject)) {
            selfTerminate();
        } else if (isEntity(otherObject)) {
            if (isNotFriendly(otherObject)) {
                selfTerminate();
            }
        }
    }

    @Override
    protected boolean isTerrain(Spatial otherObject) {
        return super.isTerrain(otherObject);
    }

    private boolean isNotFriendly(Spatial otherObject) {
        TeamId ourTeam = this.parentSpatial.getUserData(EntityDataKeys.TEAM_ID);
        TeamId theirTeam = otherObject.getUserData(EntityDataKeys.TEAM_ID);
        return !ourTeam.equals(theirTeam);
    }
}
