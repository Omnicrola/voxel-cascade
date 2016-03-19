package com.omnicrola.voxel.ui.select;

import com.jme3.font.BitmapText;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;

/**
 * Created by omnic on 3/17/2016.
 */
public class HealthBar extends Node {

    private final Geometry background;
    private final Geometry healthBar;
    private final BitmapText bitmapText;

    public HealthBar(String name, Geometry background, Geometry healthBar, BitmapText bitmapText) {
        super(name);
        this.background = background;
        this.healthBar = healthBar;
        this.bitmapText = bitmapText;
        setPercent(1.0f);
    }

    public void setPercent(float percent) {
        if (percent <= 0f) {
            healthBar.setLocalScale(0, 1f, 1f);
        }
        if (0f < percent && percent <= 1f) {
            healthBar.setLocalScale(percent, 1f, 1f);
        }
    }

    public float getPercent() {
        return healthBar.getLocalScale().getX();
    }

}
