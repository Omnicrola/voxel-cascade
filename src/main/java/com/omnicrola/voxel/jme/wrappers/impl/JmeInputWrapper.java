package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.FlyByCamera;
import com.jme3.input.controls.InputListener;
import com.jme3.math.Vector2f;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.IActionCode;
import com.omnicrola.voxel.jme.wrappers.IGameInput;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeInputWrapper implements IGameInput {
    private VoxelGameEngine game;

    public JmeInputWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void bind(IActionCode actionCode, InputListener inputListener) {
        game.getInputManager().addListener(inputListener, actionCode.trigger());
    }

    @Override
    public void unbind(InputListener inputListener) {
        game.getInputManager().removeListener(inputListener);
    }

    @Override
    public void toggleMouseGrabbed() {
        boolean isGrabbed = this.game.getInputManager().isCursorVisible();
        setMouseGrabbed(!isGrabbed);
    }

    @Override
    public Vector2f getCursorPosition() {
        return new Vector2f(this.game.getInputManager().getCursorPosition());
    }

    @Override
    public void setMouseGrabbed(boolean isGrabbed) {
        this.game.getInputManager().setCursorVisible(isGrabbed);
        FlyByCamera flyByCamera = this.game.getFlyByCamera();
        flyByCamera.setDragToRotate(!isGrabbed);
        flyByCamera.setEnabled(isGrabbed);
    }

    @Override
    public void setCursor(JmeCursor newCursor) {
        this.game.getInputManager().setMouseCursor(newCursor);
    }

    @Override
    public void setCameraMoveSpeed(float speed) {
        this.game.getFlyByCamera().setMoveSpeed(speed);
    }
}
