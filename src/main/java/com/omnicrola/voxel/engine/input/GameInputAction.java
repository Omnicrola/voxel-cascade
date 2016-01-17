package com.omnicrola.voxel.engine.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    SELECT, DEBUG_RELOAD_LEVEL, MOUSE_LOOK, MOUSE_SELECT, BUILD_1, BUILD_2, DEBUG_APPLY_FORCE;

    @Override
    public String trigger() {
        return this.toString();
    }
}
