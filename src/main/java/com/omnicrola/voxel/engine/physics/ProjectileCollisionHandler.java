package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Spatial;
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
