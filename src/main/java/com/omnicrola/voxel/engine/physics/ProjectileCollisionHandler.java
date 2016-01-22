package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class ProjectileCollisionHandler extends AbstractCollisionHandler {

    public ProjectileCollisionHandler(Spatial projectile, IGameWorld gameWorld) {
        super(projectile, gameWorld);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            doNothing();
        } else if (isTerrain(otherObject)) {
            selfTerminate();
        } else if (isEntity(otherObject)) {
            if (isEnemyUnit(otherObject)) {
                selfTerminate();
            }
        }
    }

    private boolean isEnemyUnit(Spatial otherObject) {
        TeamData ourTeam = this.parentSpatial.getUserData(EntityDataKeys.TEAM_DATA);
        TeamData theirTeam = otherObject.getUserData(EntityDataKeys.TEAM_DATA);
        return !ourTeam.equals(theirTeam);
    }
}
