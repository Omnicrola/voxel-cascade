package com.omnicrola.voxel.input.listeners;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.states.IWorldLevelManager;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.MoveSelectedUnitsStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.ui.CursorToken;

/**
 * Created by omnic on 1/24/2016.
 */
public class SetMoveCursorStrategyListener implements ActionListener {
    private IWorldLevelManager currentLevelProvider;
    private IGameGui gameGui;

    public SetMoveCursorStrategyListener(IWorldLevelManager currentLevelProvider, IGameGui gameGui) {
        this.currentLevelProvider = currentLevelProvider;
        this.gameGui = gameGui;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevel();
            WorldCursor worldCursor = currentLevelState.getWorldCursor();
            JmeCursor moveCursor = this.gameGui.build().cursor(CursorToken.MOVE);
            worldCursor.setCursorStrategy(new MoveSelectedUnitsStrategy(currentLevelState, worldCursor, moveCursor));
        }
    }
}
