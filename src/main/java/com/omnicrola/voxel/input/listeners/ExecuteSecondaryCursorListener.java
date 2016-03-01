package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.IWorldCursor;

/**
 * Created by omnic on 1/24/2016.
 */
public class ExecuteSecondaryCursorListener implements ActionListener {
    private IWorldCursor worldCursor;

    public ExecuteSecondaryCursorListener(IWorldCursor worldCursor) {
        this.worldCursor = worldCursor;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
            worldCursor.executeSecondary(isPressed);
    }
}
