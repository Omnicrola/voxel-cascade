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
    CAMERA_FORWARD,
    CAMERA_BACKWARD,
    CAMERA_LEFT,
    CAMERA_RIGHT, CAMERA_UP, CAMERA_DOWN;

    @Override
    public String trigger() {
        return this.toString();
    }
}
