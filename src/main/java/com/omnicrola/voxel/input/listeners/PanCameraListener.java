package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.omnicrola.voxel.engine.CameraDolly;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.input.GameInputAction;

/**
 * Created by Eric on 1/24/2016.
 */
public class PanCameraListener implements ActionListener, AnalogListener {

    private CameraDolly camera;

    public PanCameraListener(CameraDolly camera) {
        this.camera = camera;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
    }

    private void moveCamera(float movementDistance, boolean sideways) {
        this.camera.moveCamera(movementDistance, sideways);

    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (name.equals(GameInputAction.CAMERA_LEFT.toString())) {
            moveCamera(-value, true);
        } else if (name.equals(GameInputAction.CAMERA_RIGHT.toString())) {
            moveCamera(value, true);
        } else if (name.equals(GameInputAction.CAMERA_FORWARD.toString())) {
            moveCamera(-value, false);
        } else if (name.equals(GameInputAction.CAMERA_BACKWARD.toString())) {
            moveCamera(value, false);
        }
    }

    public void registerInputs(ActivePlayState activePlayState) {
        activePlayState.addStateInput(GameInputAction.CAMERA_FORWARD, this);
        activePlayState.addStateInput(GameInputAction.CAMERA_BACKWARD, this);
        activePlayState.addStateInput(GameInputAction.CAMERA_LEFT, this);
        activePlayState.addStateInput(GameInputAction.CAMERA_RIGHT, this);
        activePlayState.addStateInput(GameInputAction.CAMERA_UP, this);
        activePlayState.addStateInput(GameInputAction.CAMERA_DOWN, this);
    }
}
