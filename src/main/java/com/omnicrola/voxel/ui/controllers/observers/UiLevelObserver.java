package com.omnicrola.voxel.ui.controllers.observers;

import com.omnicrola.voxel.data.ILevelObserver;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.ui.controllers.ActivePlayScreenController;

/**
 * Created by omnic on 2/11/2016.
 */
public class UiLevelObserver implements ILevelObserver {
    private ActivePlayScreenController activePlayScreenController;

    public UiLevelObserver(ActivePlayScreenController activePlayScreenController) {
        this.activePlayScreenController = activePlayScreenController;
    }

    @Override
    public void levelUpdated(LevelState currentLevel) {
        this.activePlayScreenController.updateStats(currentLevel);
    }
}
