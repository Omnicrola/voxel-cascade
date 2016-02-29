package com.omnicrola.voxel.fx;

/**
 * Created by omnic on 1/31/2016.
 */
public enum MaterialToken {
    BUILD_VALID("build-valid.png", true),
    BUILD_NOT_VALID("build-not-valid.png", true);

    private String texture;
    private boolean transparent;

    MaterialToken(String texture, boolean transparent) {
        this.texture = texture;
        this.transparent = transparent;
    }

    public String texture() {
        return this.texture;
    }

    public boolean isTransparent() {
        return transparent;
    }
}
