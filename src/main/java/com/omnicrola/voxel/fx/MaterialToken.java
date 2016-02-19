package com.omnicrola.voxel.fx;

import com.jme3.math.ColorRGBA;

/**
 * Created by omnic on 1/31/2016.
 */
public enum MaterialToken {
    BUILD_VALID("build-valid.png", true),
    BUILD_NOT_VALID("build-not-valid.png", true),
    TERRAIN_VOXEL(ColorRGBA.randomColor(), false);

    private ColorRGBA color;
    private String texture;
    private boolean transparent;

    MaterialToken(String texture, boolean transparent) {
        this.texture = texture;
        this.transparent = transparent;
    }

    MaterialToken(ColorRGBA color, boolean transparent) {
        this.color = color;
        this.transparent = transparent;
    }

    public String texture() {
        return this.texture;
    }

    public ColorRGBA color() {
        return color;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
