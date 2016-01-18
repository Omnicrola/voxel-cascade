package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;

/**
 * Created by omnic on 1/17/2016.
 */
public class TerrainCollisionHandler extends AbstractCollisionHandler {
    public TerrainCollisionHandler(Geometry voxel, IGamePhysics physicsSpace) {
        super(voxel, physicsSpace);
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
