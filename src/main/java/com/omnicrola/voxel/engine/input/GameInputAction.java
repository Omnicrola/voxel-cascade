package com.omnicrola.voxel.engine.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    SELECT, RELOAD_LEVEL, MOUSE_LOOK;

    @Override
    public String trigger() {
        return this.toString();
    }
}