package com.omnicrola.voxel.input.listeners;

import com.omnicrola.voxel.jme.wrappers.IGameGui;

/**
 * Created by Eric on 1/24/2016.
 */
public class PanCameraForwardListener extends CameraTranslationListener {


    public PanCameraForwardListener(IGameGui gameGui) {
        super(gameGui);
    }

    @Override
    protected void moveCamera(float movementDistance) {
        this.gameGui.moveCamera(movementDistance, false);
    }
}
