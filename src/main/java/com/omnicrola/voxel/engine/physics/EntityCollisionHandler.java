package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityCollisionHandler extends AbstractCollisionHandler {
    public EntityCollisionHandler(Geometry geometry, IGamePhysics physicsSpace) {
        super(geometry, physicsSpace);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            if (isNotOurOwnProjectile(otherObject)) {
                takeDamage(otherObject);
            }
        } else if (isTerrain(otherObject)) {
            doNothing();
        } else if (isEntity(otherObject)) {
            doNothing();
        }
    }

    private boolean isNotOurOwnProjectile(Spatial otherObject) {
        Object projectileEmitter = otherObject.getUserData(EntityDataKeys.PROJECTILE_OWNER_SPATIAL);
        return this.parentSpatial != projectileEmitter;
    }

    private void takeDamage(Spatial otherObject) {
        float damage = getFloat(otherObject, EntityDataKeys.PROJECTILE_DAMAGE);
        float hp = getFloat(EntityDataKeys.HITPOINTS);
        System.out.println("taking damage: " + damage+ " from " +otherObject.getName());
        float newHp = hp - damage;
        this.parentSpatial.setUserData(EntityDataKeys.HITPOINTS, newHp);
        if (newHp <= 0) {
            System.out.println("dead, no HP");
            selfTerminate();
        }
    }
}
