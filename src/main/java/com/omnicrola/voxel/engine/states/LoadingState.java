package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.FlyByCamera;
import com.jme3.input.InputManager;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class LoadingState extends AbstractAppState {

    private static final float ONE_SECOND = 1.0f;
    private GLabel loadingText;
    private float timeElapsed;
    private AppStateManager stateManager;

    public LoadingState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.stateManager = stateManager;
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        InputManager inputManager = app.getInputManager();
        FlyByCamera flyByCamera = voxelGameEngine.getFlyByCamera();
        inputManager.setCursorVisible(false);
        flyByCamera.setDragToRotate(true);
        flyByCamera.setEnabled(false);
    }

    @Override
    public void update(float tpf) {
        this.timeElapsed += tpf;
        if (timeElapsed > ONE_SECOND) {
            this.stateManager.getState(MainMenuState.class).setEnabled(true);
            setEnabled(false);
        }
    }
}
