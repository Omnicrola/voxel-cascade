package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.omnicrola.voxel.engine.states.ActivePlayInputState;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.jme.wrappers.IGameInput;

/**
 * Created by Eric on 1/24/2016.
 */
public class PanCameraListener implements ActionListener, AnalogListener {

    private IGameGui gameGui;
    private IGameInput input;

    public PanCameraListener(IGameGui gameGui, IGameInput input) {
        this.gameGui = gameGui;
        this.input = input;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
    }

    private void moveCamera(float movementDistance, boolean sideways) {
        this.gameGui.moveCamera(movementDistance, sideways);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals(GameInputAction.CAMERA_LEFT.toString())) {
            moveCamera(value, true);
        } else if (name.equals(GameInputAction.CAMERA_RIGHT.toString())) {
            moveCamera(-value, true);
        } else if (name.equals(GameInputAction.CAMERA_FORWARD.toString())) {
            moveCamera(value, false);
        } else if (name.equals(GameInputAction.CAMERA_BACKWARD.toString())) {
            moveCamera(-value, false);
        }
    }

    public void registerInputs(ActivePlayInputState activePlayInputState) {
        activePlayInputState.addStateInput(GameInputAction.CAMERA_FORWARD, this);
        activePlayInputState.addStateInput(GameInputAction.CAMERA_BACKWARD, this);
        activePlayInputState.addStateInput(GameInputAction.CAMERA_LEFT, this);
        activePlayInputState.addStateInput(GameInputAction.CAMERA_RIGHT, this);
        activePlayInputState.addStateInput(GameInputAction.CAMERA_UP, this);
        activePlayInputState.addStateInput(GameInputAction.CAMERA_DOWN, this);
    }
}
