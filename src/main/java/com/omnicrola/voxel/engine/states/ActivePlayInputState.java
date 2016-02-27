package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.CameraDolly;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.ClearSelectionListener;
import com.omnicrola.voxel.input.listeners.ExecutePrimaryCursorListener;
import com.omnicrola.voxel.input.listeners.ExecuteSecondaryCursorListener;
import com.omnicrola.voxel.input.listeners.PanCameraListener;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayInputState extends AbstractAppState {
    private LevelManager levelManager;
    private InputManager inputManager;

    public ActivePlayInputState(LevelManager levelManager) {
        this.levelManager = levelManager;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        this.inputManager = app.getInputManager();
        voxelGameEngine.getFlyByCamera().setMoveSpeed(10f);
        voxelGameEngine.getNiftyGui().gotoScreen(UiScreen.ACTIVE_PLAY.toString());

        initializeKeybindings(voxelGameEngine);
        setEnabled(false);
    }

    private void initializeKeybindings(VoxelGameEngine voxelGameEngine) {

        addStateInput(GameInputAction.CLEAR_SELECTION, new ClearSelectionListener(this.levelManager));

        ExecutePrimaryCursorListener primaryCursorListener = new ExecutePrimaryCursorListener(this.levelManager);
        addStateInput(GameInputAction.MULTI_SELECT, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_PRIMARY, primaryCursorListener);
        addStateInput(GameInputAction.MOUSE_SECONDARY, new ExecuteSecondaryCursorListener(this.levelManager));

        PanCameraListener panCameraListener = new PanCameraListener(new CameraDolly(voxelGameEngine.getCamera()));
        panCameraListener.registerInputs(this);
    }

    public void addStateInput(GameInputAction gameInputAction, ActionListener actionListener) {
        this.inputManager.addListener(actionListener, gameInputAction.toString());
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.levelManager.getCurrentLevel().addTime(tpf);
    }

}
