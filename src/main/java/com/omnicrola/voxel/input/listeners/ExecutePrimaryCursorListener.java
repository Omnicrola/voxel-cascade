package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by omnic on 1/24/2016.
 */
public class ExecutePrimaryCursorListener implements ActionListener {
    private ILevelManager currentLevelProvider;
    private boolean isMultiSelecting;

    public ExecutePrimaryCursorListener(ILevelManager currentLevelProvider) {
        this.currentLevelProvider = currentLevelProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(GameInputAction.MULTI_SELECT.toString())) {
            this.isMultiSelecting = isPressed;
        } else if (name.equals(GameInputAction.MOUSE_PRIMARY.toString())) {
            LevelState currentLevelState = this.currentLevelProvider.getCurrentLevel();
            if (currentLevelState != null) {
                WorldCursor worldCursor = currentLevelState.getWorldCursor();
                GameMouseEvent gameMouseEvent = new GameMouseEvent(isPressed, isMultiSelecting);
                worldCursor.executePrimary(gameMouseEvent);
            }
        }
    }
}
