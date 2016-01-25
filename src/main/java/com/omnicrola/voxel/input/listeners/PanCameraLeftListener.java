package com.omnicrola.voxel.input.listeners;

import com.omnicrola.voxel.jme.wrappers.IGameGui;

/**
 * Created by Eric on 1/24/2016.
 */
public class PanCameraLeftListener extends CameraTranslationListener {
    public PanCameraLeftListener(IGameGui gameGui) {
        super(gameGui);
    }

    @Override
    protected void moveCamera(float movementDistance) {
        this.gameGui.moveCamera(movementDistance, true);
    }
}
