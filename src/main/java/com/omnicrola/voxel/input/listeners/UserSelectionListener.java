package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector2f;
import com.omnicrola.voxel.input.ScreenRectangle;
import com.omnicrola.voxel.input.UserInteractionHandler;
import com.omnicrola.voxel.jme.wrappers.IGameInput;

/**
 * Created by omnic on 1/17/2016.
 */
public class UserSelectionListener implements ActionListener {

    private boolean wasPressed;
    private IGameInput input;
    private UserInteractionHandler userInteractionHandler;
    private Vector2f lastCursorPosition;

    public UserSelectionListener(IGameInput input, UserInteractionHandler userInteractionHandler) {
        this.input = input;
        this.userInteractionHandler = userInteractionHandler;
        this.wasPressed = false;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!this.wasPressed && isPressed) {
            this.lastCursorPosition = this.input.getCursorPosition();
        } else if (!isPressed) {
            if (mouseHasBeenDragged()) {
                ScreenRectangle screenRectangle = new ScreenRectangle(this.lastCursorPosition, this.input.getCursorPosition());
                this.userInteractionHandler.selectArea(screenRectangle);
            } else {
                this.userInteractionHandler.selectPoint();
            }
        }
        this.wasPressed = isPressed;
    }

    private boolean mouseHasBeenDragged() {
        Vector2f cursorPosition = this.input.getCursorPosition();
        float distance = cursorPosition.distance(this.lastCursorPosition);
        return distance > 1;
    }
}
