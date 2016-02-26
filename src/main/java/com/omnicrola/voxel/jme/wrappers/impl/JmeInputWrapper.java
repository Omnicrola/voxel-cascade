package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.jme3.input.controls.InputListener;
import com.jme3.math.Vector2f;
import com.omnicrola.voxel.input.IActionCode;
import com.omnicrola.voxel.jme.wrappers.IGameInput;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeInputWrapper implements IGameInput {
    private final InputManager inputManager;
    private final FlyByCamera flyByCamera;

    public JmeInputWrapper(InputManager inputManager, FlyByCamera flyByCamera) {

        this.inputManager = inputManager;
        this.flyByCamera = flyByCamera;
    }

    @Override
    public void bind(IActionCode actionCode, InputListener inputListener) {
        this.inputManager.addListener(inputListener, actionCode.trigger());
    }

    @Override
    public void unbind(InputListener inputListener) {
        this.inputManager.removeListener(inputListener);
    }

    @Override
    public void toggleMouseGrabbed() {
        boolean isGrabbed = this.inputManager.isCursorVisible();
        setMouseGrabbed(!isGrabbed);
    }

    @Override
    public Vector2f getCursorPosition() {
        return new Vector2f(this.inputManager.getCursorPosition());
    }

    @Override
    public void setMouseGrabbed(boolean isGrabbed) {
        this.inputManager.setCursorVisible(isGrabbed);
        this.flyByCamera.setDragToRotate(!isGrabbed);
        this.flyByCamera.setEnabled(isGrabbed);
    }

    @Override
    public void setCursor(JmeCursor newCursor) {
        this.inputManager.setMouseCursor(newCursor);
    }

    @Override
    public void setCameraMoveSpeed(float speed) {
        this.flyByCamera.setMoveSpeed(speed);
    }
}
