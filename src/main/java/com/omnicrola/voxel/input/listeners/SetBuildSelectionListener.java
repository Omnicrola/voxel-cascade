package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.UserInteractionHandler;

/**
 * Created by omnic on 1/17/2016.
 */
public class SetBuildSelectionListener implements ActionListener {
    private int buildType;
    private UserInteractionHandler userInteractionHandler;

    public SetBuildSelectionListener(int buildType, UserInteractionHandler userInteractionHandler) {
        this.buildType = buildType;
        this.userInteractionHandler = userInteractionHandler;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.userInteractionHandler.setBuildMode(buildType);
        }
    }
}
