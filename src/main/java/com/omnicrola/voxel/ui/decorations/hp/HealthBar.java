package com.omnicrola.voxel.ui.decorations.hp;

import com.jme3.font.BitmapText;
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


    private Geometry foreground;
    private final BitmapText bitmapText;
    private Spatial targetUnit;

    public HealthBar(Geometry foreground, BitmapText bitmapText) {
        this.foreground = foreground;
        this.bitmapText = bitmapText;
    }

    public void setLabel(String label) {
        this.bitmapText.setText(label);
    }

    public void setTargetUnit(Spatial targetUnit) {
        this.targetUnit = targetUnit;
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        if (this.targetUnit != null) {
            updateBarPosition();
        }
    }

    private void updateBarPosition() {
        Vector3f targetPosition = this.targetUnit.getWorldTranslation();
        this.setLocalTranslation(targetPosition);
        float hitpoints = VoxelUtil.floatData(this.targetUnit, EntityDataKeys.HITPOINTS);
        float maxHitpoints = VoxelUtil.floatData(this.targetUnit, EntityDataKeys.MAX_HITPOINTS);
        float percentage = maxHitpoints / hitpoints;
        setPercent(percentage);
    }

    private void setPercent(float percent) {
        if (percent <= 0f) {
            this.foreground.setLocalScale(0, 1f, 1f);
        }
        if (0f < percent && percent <= 1f) {
            this.foreground.setLocalScale(percent, 1f, 1f);
        }
    }

    @Override
    public void attachTo(Spatial spatial) {
        this.targetUnit = spatial;
    }

    @Override
    public void detatchFrom(Spatial spatial) {
        this.targetUnit = null;
    }
}
