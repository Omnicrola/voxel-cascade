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
    MOUSE_LOOK,
    MOUSE_SELECT, CLEAR_SELECTION, TOGGLE_PHYSICS_DEBUG;

    @Override
    public String trigger() {
        return this.toString();
    }
}
