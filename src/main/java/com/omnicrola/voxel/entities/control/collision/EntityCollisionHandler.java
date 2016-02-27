package com.omnicrola.voxel.entities.control.collision;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityCollisionHandler extends AbstractCollisionHandler {
    public EntityCollisionHandler(Spatial spatial, WorldManager worldManager) {
        super(spatial, worldManager);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            if (isEnemyProjectile(otherObject)) {
                takeDamage(otherObject);
            }
        } else if (isTerrain(otherObject)) {
            doNothing();
        } else if (isEntity(otherObject)) {
            doNothing();
        }
    }

    private boolean isEnemyProjectile(Spatial otherObject) {
        TeamData ourTeam = this.parentSpatial.getUserData(EntityDataKeys.TEAM_DATA);
        TeamData theirTeam = otherObject.getUserData(EntityDataKeys.TEAM_DATA);
        return !ourTeam.equals(theirTeam);
    }

    private void takeDamage(Spatial otherObject) {
        float damage = getFloat(otherObject, EntityDataKeys.PROJECTILE_DAMAGE);
        float hp = getFloat(EntityDataKeys.HITPOINTS);
        float newHp = hp - damage;
        this.parentSpatial.setUserData(EntityDataKeys.HITPOINTS, newHp);
        if (newHp <= 0) {
            selfTerminate();
        }
    }
}
