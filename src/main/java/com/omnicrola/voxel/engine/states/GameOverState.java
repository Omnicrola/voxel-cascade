package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.QuitActiveGameListener;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends AbstractAppState {

    private VoxelGameEngine voxelGameEngine;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.voxelGameEngine = (VoxelGameEngine) app;
        this.setEnabled(false);
        this.voxelGameEngine.getInputManager().addListener(new QuitActiveGameListener(stateManager), GameInputAction.SELECT.toString());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.voxelGameEngine.getNiftyGui().gotoScreen(UiScreen.GAME_OVER.toString());
        }
    }
}
