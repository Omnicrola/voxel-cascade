package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */
public class TerrainCollisionHandler extends AbstractCollisionHandler {
    public TerrainCollisionHandler(Spatial voxel, IGameWorld gameWorld) {
        super(voxel, gameWorld);
    }

    @Override
    public void collideWith(Spatial otherObject) {
        if (isProjectile(otherObject)) {
            doNothing();
        } else if (isTerrain(otherObject)) {
            doNothing();
        } else if (isEntity(otherObject)) {
            doNothing();
        }
    }
}
