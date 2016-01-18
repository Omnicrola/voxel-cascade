package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class ProjectileCollisionHandler extends AbstractCollisionHandler {

    public ProjectileCollisionHandler(Geometry projectile, IGamePhysics physicsSpace) {
        super(projectile, physicsSpace);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            doNothing();
        } else if (isTerrain(otherObject)) {
            System.out.println("dead, hit terrain");
            selfTerminate();
        } else if (isEntity(otherObject)) {
            if (isNotOurEmitter(otherObject)) {
                System.out.println("dead, hit an enemy");
                selfTerminate();
            }
        }
    }

    private boolean isNotOurEmitter(Spatial otherObject) {
        Object userData = this.parentSpatial.getUserData(EntityDataKeys.PROJECTILE_OWNER_SPATIAL);
        return userData != otherObject;
    }
}
