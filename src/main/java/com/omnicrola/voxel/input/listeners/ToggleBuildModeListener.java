package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.ui.IGameUserInterface;

/**
 * Created by Eric on 1/24/2016.
 */
public class ToggleBuildModeListener implements ActionListener {

    private IGameUserInterface userInterface;

    public ToggleBuildModeListener(IGameUserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        this.userInterface.setBuildMode(!this.userInterface.isInBuildMode());
    }
}
