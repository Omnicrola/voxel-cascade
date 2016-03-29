package com.omnicrola.voxel.entities.control.collision;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.world.WorldManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by omnic on 1/17/2016.
 */
public abstract class AbstractCollisionHandler implements ISpatialCollisionHandler {

    private final WorldManager worldManager;
    protected Spatial parentSpatial;
    private List<IDeathAction> deathActions;

    public AbstractCollisionHandler(Spatial parentSpatial, WorldManager worldManager) {
        this.parentSpatial = parentSpatial;
        this.worldManager = worldManager;
        this.deathActions = new ArrayList<>();
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
        Node parent = this.parentSpatial.getParent();
        if (parent != null) {
            this.deathActions.forEach(d -> d.destruct(this.parentSpatial));
            parent.detachChild(this.parentSpatial);
        }
        this.parentSpatial.setUserData(EntityDataKeys.IS_COLLIDABLE, false);
        this.worldManager.removeSpatial(this.parentSpatial);
    }

    public void addDeathAction(IDeathAction deathAction) {
        this.deathActions.add(deathAction);
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
