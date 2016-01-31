package com.omnicrola.voxel.fx;

/**
 * Created by omnic on 1/31/2016.
 */
public enum MaterialToken {
    GHOST_BUILDING("ghost.png", true);

    private String texture;
    private boolean transparent;

    private MaterialToken(String texture, boolean transparent) {
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
