package com.omnicrola.voxel.ui.decorations.hp;

import com.jme3.bounding.BoundingBox;
import com.jme3.bounding.BoundingSphere;
import com.jme3.bounding.BoundingVolume;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.ui.decorations.IDecoration;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by omnic on 3/17/2016.
 */
public class HealthBar extends Node implements IDecoration {


    private final Vector3f vecStore;
    private Geometry foreground;
    private final BitmapText bitmapText;
    private Spatial targetUnit;

    public HealthBar(Geometry foreground, BitmapText bitmapText) {
        this.foreground = foreground;
        this.bitmapText = bitmapText;
        this.vecStore = new Vector3f();
    }

    public void setLabel(String label) {
        this.bitmapText.setText(label);
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        if (this.targetUnit != null) {
            updateBarPosition();
            updatePercentage();
        }
    }

    private void updateBarPosition() {
        BoundingVolume worldBound = this.targetUnit.getWorldBound();
        worldBound.getCenter(vecStore);
        if (worldBound.getType().equals(BoundingVolume.Type.AABB)) {
            BoundingBox box = (BoundingBox) worldBound;
            float yExtent = box.getYExtent();
            vecStore.addLocal(0, yExtent, 0);
        } else if (worldBound.getType().equals(BoundingVolume.Type.Sphere)) {
            BoundingSphere boundingSphere = (BoundingSphere) worldBound;
            float radius = boundingSphere.getRadius();
            vecStore.addLocal(0, radius, 0);
        }
        vecStore.addLocal(0, 0.5f, 0);
        this.setLocalTranslation(vecStore);
    }


    private void updatePercentage() {
        float hitpoints = VoxelUtil.floatData(this.targetUnit, EntityDataKeys.HITPOINTS);
        float maxHitpoints = VoxelUtil.floatData(this.targetUnit, EntityDataKeys.MAX_HITPOINTS);
        float percentage = hitpoints / maxHitpoints;
        setPercent(percentage);
    }

    private void setPercent(float percent) {
        if (percent <= 0f) {
            this.foreground.setLocalScale(0, 1f, 1f);
        }
        if (0f < percent && percent <= 1f) {
            this.foreground.setLocalScale(percent, 1f, 1f);
            if (percent < 0.2f) {
                setBarColor(ColorRGBA.Red);
            } else {
                setBarColor(ColorRGBA.Green);
            }
        }
    }

    private void setBarColor(ColorRGBA color) {
        this.foreground.getMaterial().setColor("Color", color);
    }

    @Override
    public void attachTo(Spatial spatial) {
        this.targetUnit = spatial;
    }

    @Override
    public void detatchFrom(Spatial spatial) {
        this.targetUnit = null;
    }

    @Override
    public void removeFromWorld() {
        if (this.parent != null) {
            this.parent.detachChild(this);
        }
    }
}
