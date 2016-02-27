package com.omnicrola.voxel.entities.control.collision;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/17/2016.
 */
public class TerrainCollisionHandler extends AbstractCollisionHandler {
    public TerrainCollisionHandler(Spatial voxel, WorldManager worldManager) {
        super(voxel, worldManager);
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
