package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by omnic on 1/17/2016.
 */
public class ClearSelectionListener implements ActionListener {
    private ICurrentLevelProvider currentLevelProvider;

    public ClearSelectionListener(ICurrentLevelProvider currentLevelProvider) {
        this.currentLevelProvider = currentLevelProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevelState();
            WorldCursor worldCursor = currentLevelState.getWorldCursor();
            worldCursor.clearCursorStrategy();
            worldCursor.clearSelection();
        }
    }
}
