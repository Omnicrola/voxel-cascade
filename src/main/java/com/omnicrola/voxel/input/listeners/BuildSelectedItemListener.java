package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.UserInteractionHandler;

/**
 * Created by omnic on 1/24/2016.
 */
public class BuildSelectedItemListener implements ActionListener {
    private final UserInteractionHandler userInteractionHandler;
    private final int index;

    public BuildSelectedItemListener(UserInteractionHandler userInteractionHandler, int index) {
        this.userInteractionHandler = userInteractionHandler;
        this.index = index;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if(!isPressed ){
        }
    }
}
