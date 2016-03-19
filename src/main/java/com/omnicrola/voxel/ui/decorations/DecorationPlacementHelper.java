package com.omnicrola.voxel.ui.decorations;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by Eric on 3/19/2016.
 */
public class DecorationPlacementHelper {
    private final Vector3f centerStore = new Vector3f();
    private final Vector3f adjustmentStore = new Vector3f();
    private Vector3f offset;
    private Position position = Position.TOP;

    public DecorationPlacementHelper(Vector3f offset) {
        this.offset = offset;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void adjustPosition(Spatial spatialToAdjust, Spatial targetUnit) {
        BoundingVolume worldBound = targetUnit.getWorldBound();
        worldBound.getCenter(centerStore);
        Vector3f adjustment = getAdjustment(worldBound);
        if (this.position.equals(Position.TOP)) {
            centerStore.addLocal(adjustment);
        } else {
            centerStore.subtractLocal(adjustment);
        }
        centerStore.addLocal(offset);
        spatialToAdjust.setLocalTranslation(centerStore);
    }

    private Vector3f getAdjustment(BoundingVolume worldBound) {
        adjustmentStore.set(0, 0, 0);
        if (worldBound.getType().equals(BoundingVolume.Type.AABB)) {
            BoundingBox box = (BoundingBox) worldBound;
            float yExtent = box.getYExtent();
            adjustmentStore.addLocal(0, yExtent, 0);
        } else if (worldBound.getType().equals(BoundingVolume.Type.Sphere)) {
            BoundingSphere boundingSphere = (BoundingSphere) worldBound;
            float radius = boundingSphere.getRadius();
            adjustmentStore.addLocal(0, radius, 0);
        }
        return adjustmentStore;
    }

    public enum Position {
        TOP, BOTTOM;
    }
}
