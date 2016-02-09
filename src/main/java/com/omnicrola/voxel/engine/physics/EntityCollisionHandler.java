package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityCollisionHandler extends AbstractCollisionHandler {
    public EntityCollisionHandler(Spatial spatial, IGameWorld physicsSpace) {
        super(spatial, physicsSpace);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            if (isEnemyProjectile(otherObject)) {
                System.out.println(this.parentSpatial.getName()+ " hit by: " + otherObject.getName() + " " + otherObject.hashCode());
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
