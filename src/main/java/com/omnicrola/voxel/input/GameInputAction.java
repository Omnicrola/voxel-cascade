package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    SELECT,
    DEBUG_RELOAD_LEVEL,
    ORDER_MOVE,
    ORDER_ATTACK,
    ORDER_STOP,
    MOUSE_SECONDARY,
    MOUSE_PRIMARY, CLEAR_SELECTION, TOGGLE_PHYSICS_DEBUG, ORDER_BUILD_MODE, ORDER_BUILD_SELECT_1, ORDER_BUILD_SELECT_2;

    @Override
    public String trigger() {
        return this.toString();
    }
}
