package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/31/2016.
 */
public class GameMouseEvent {
    private final boolean isPressed;
    private final boolean isMultiSelecting;

    public GameMouseEvent(boolean isPressed, boolean isMultiSelecting) {
        this.isPressed = isPressed;
        this.isMultiSelecting = isMultiSelecting;
    }

    public boolean isPressed() {
        return isPressed;
    }

    public boolean isMultiSelecting() {
        return isMultiSelecting;
    }
}
