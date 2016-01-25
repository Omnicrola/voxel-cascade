package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    MOUSE_SECONDARY,
    MOUSE_PRIMARY,
    MOUSE_MOVEMENT,
    SELECT,
    DEBUG_RELOAD_LEVEL,
    ORDER_MOVE,
    ORDER_ATTACK,
    ORDER_STOP,
    CLEAR_SELECTION,
    TOGGLE_PHYSICS_DEBUG,
    ORDER_BUILD_MODE,
    DEBUG_TOGGLE_MOUSE_LOOK,
    ARROW_UP,
    ARROW_DOWN,
    ARROW_LEFT,
    ARROW_RIGHT;

    @Override
    public String trigger() {
        return this.toString();
    }
}
