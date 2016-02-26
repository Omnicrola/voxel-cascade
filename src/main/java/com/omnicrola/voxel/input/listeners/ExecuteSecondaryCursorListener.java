package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ILevelManager;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by omnic on 1/24/2016.
 */
public class ExecuteSecondaryCursorListener implements ActionListener {
    private ILevelManager currentLevelProvider;

    public ExecuteSecondaryCursorListener(ILevelManager currentLevelProvider) {
        this.currentLevelProvider = currentLevelProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevel();
            WorldCursor worldCursor = currentLevelState.getWorldCursor();
            worldCursor.executeSecondary(isPressed);
    }
}
