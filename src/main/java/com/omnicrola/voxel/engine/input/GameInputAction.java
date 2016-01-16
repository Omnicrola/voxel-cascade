package com.omnicrola.voxel.engine.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    SELECT, RELOAD_LEVEL, MOUSE_LOOK, MOUSE_SELECT, BUILD_1, BUILD_2, BUILD_3;

    @Override
    public String trigger() {
        return this.toString();
    }
}
