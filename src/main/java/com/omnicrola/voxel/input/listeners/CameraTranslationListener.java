package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.jme.wrappers.IGameGui;

/**
 * Created by Eric on 1/24/2016.
 */
public abstract class CameraTranslationListener implements ActionListener {

    protected IGameGui gameGui;


    public CameraTranslationListener(IGameGui gameGui) {
        this.gameGui = gameGui;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        float movementDistance = tpf * 2f;
        moveCamera(movementDistance);
    }

    protected abstract void moveCamera(float movementDistance);
}
