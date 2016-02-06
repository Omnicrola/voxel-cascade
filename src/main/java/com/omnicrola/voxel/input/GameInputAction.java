package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameInputAction implements IActionCode {
    TOGGLE_DEBUG_MODE,
    DEBUG_RELOAD_LEVEL,
    DEBUG_TOGGLE_MOUSE_LOOK,
    DEBUG_TOGGLE_PHYSICS,
    DEBUG_SCENE_GRAPH,
    DEBUG_TARGET_OBJECT,
    DEBUG_REBUILD_TERRAIN,

    MOUSE_SECONDARY,
    MOUSE_PRIMARY,
    MOUSE_MOVEMENT,
    SELECT,
    CLEAR_SELECTION,
    CAMERA_FORWARD,
    CAMERA_BACKWARD,
    CAMERA_LEFT,
    CAMERA_RIGHT,
    CAMERA_UP,
    CAMERA_DOWN,

    MULTI_SELECT, DEBUG_TOGGLE_WIREFRAME;

    @Override
    public String trigger() {
        return this.toString();
    }
}
