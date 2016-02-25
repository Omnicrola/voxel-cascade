package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by Eric on 2/24/2016.
 */
public class UiState extends AbstractAppState implements IUiManager {

    private VoxelGameEngine voxelGameEngine;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.voxelGameEngine = (VoxelGameEngine) app;
    }

    @Override
    public void changeScreen(UiScreen uiScreen) {
        this.voxelGameEngine.getNiftyGui().gotoScreen(uiScreen.toString());
    }
}
