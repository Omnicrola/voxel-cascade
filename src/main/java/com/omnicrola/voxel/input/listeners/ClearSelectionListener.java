package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.IWorldCursor;

/**
 * Created by omnic on 1/17/2016.
 */
public class ClearSelectionListener implements ActionListener {
    private IWorldCursor worldCursor;

    public ClearSelectionListener(IWorldCursor worldCursor) {
        this.worldCursor = worldCursor;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            worldCursor.clearCursorStrategy();
            worldCursor.clearSelection();
        }
    }
}
