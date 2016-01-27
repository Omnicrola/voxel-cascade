package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.ILevelChangeObserver;
import com.omnicrola.voxel.ui.controllers.ActivePlayScreenController;

/**
 * Created by Eric on 1/26/2016.
 */
public class UiLevelChangeObserver implements ILevelChangeObserver {
    private ActivePlayScreenController activePlayScreenController;

    public UiLevelChangeObserver(ActivePlayScreenController activePlayScreenController) {
        this.activePlayScreenController = activePlayScreenController;
    }

    @Override
    public void levelChanged(LevelState currentLevelState) {
        this.activePlayScreenController.setLevel(currentLevelState);
    }
}
