package com.omnicrola.voxel.engine.physics;

import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.ISpatialCollisionHandler;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public abstract class AbstractCollisionHandler implements ISpatialCollisionHandler {

    protected Geometry parentSpatial;
    private IGamePhysics physicsSpace;

    public AbstractCollisionHandler(Geometry parentSpatial, IGamePhysics physicsSpace) {
        this.parentSpatial = parentSpatial;
        this.physicsSpace = physicsSpace;
    }

    protected boolean isProjectile(Spatial otherObject) {
        return getBoolean(otherObject, EntityDataKeys.IS_PROJECTILE);
    }

    protected boolean isTerrain(Spatial otherObject) {
        return getBoolean(otherObject, EntityDataKeys.IS_TERRAIN);
    }

    protected boolean isEntity(Spatial otherObject) {
        return getBoolean(otherObject, EntityDataKeys.IS_SELECTABLE);
    }

    protected void doNothing() {
    }

    protected void selfTerminate() {
        System.out.println("terimating entity");
        Node parent = this.parentSpatial.getParent();
        if (parent != null) {
            parent.detachChild(this.parentSpatial);
        }
        physicsSpace.remove(this.parentSpatial);
    }

    protected boolean getBoolean(String dataKey) {
        return getBoolean(this.parentSpatial, dataKey);
    }

    protected boolean getBoolean(Spatial spatial, String dataKey) {
        Boolean userData = spatial.getUserData(dataKey);
        if (userData == null) {
            return false;
        }
        return userData.booleanValue();
    }

    protected float getFloat(String dataKey) {
        return getFloat(this.parentSpatial, dataKey);
    }

    protected float getFloat(Spatial spatial, String dataKey) {
        Float userData = spatial.getUserData(dataKey);
        if (userData == null) {
            return 0.0f;
        }
        return userData.floatValue();
    }
}
