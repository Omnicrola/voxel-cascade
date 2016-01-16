package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.KeyTrigger;
import com.omnicrola.voxel.engine.input.GameAction;
import com.omnicrola.voxel.engine.states.LoadingState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.engine.states.ActivePlayState;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {
    public static void initializeGame(AppStateManager stateManager) {
        createStates(stateManager);
        createInputMappings(stateManager);
    }

    private static void createStates(AppStateManager stateManager) {
        BulletAppState bulletAppState = new BulletAppState();
        LoadingState loadingState = new LoadingState();
        ActivePlayState playState = new ActivePlayState();
        MainMenuState mainMenuState = new MainMenuState(playState);

        stateManager.attach(loadingState);
        stateManager.attach(bulletAppState);
        stateManager.attach(mainMenuState);
        stateManager.attach(playState);
    }

    private static void createInputMappings(AppStateManager stateManager) {
        InputManager inputManager = stateManager.getApplication().getInputManager();
        inputManager.addMapping(GameAction.SELECT.trigger(), new KeyTrigger(KeyInput.KEY_RETURN));
        inputManager.addMapping(GameAction.RELOAD_LEVEL.trigger(), new KeyTrigger(KeyInput.KEY_R));
        inputManager.addMapping(GameAction.TOGGLE_MOUSE_GRAB.trigger(), new KeyTrigger(KeyInput.KEY_TAB));
    }
}
