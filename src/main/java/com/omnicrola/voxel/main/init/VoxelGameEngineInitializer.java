package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.engine.states.LoadingState;
import com.omnicrola.voxel.engine.states.MainMenuState;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {
    public static void initializeGame(AppStateManager stateManager) {
        createStates(stateManager);
        createInputMappings(stateManager);
    }

    private static void createStates(AppStateManager stateManager) {
        LoadingState loadingState = new LoadingState();
        ActivePlayState playState = new ActivePlayState();
        MainMenuState mainMenuState = new MainMenuState(playState);

        stateManager.attach(loadingState);
        stateManager.attach(mainMenuState);
        stateManager.attach(playState);
    }

    private static void createInputMappings(AppStateManager stateManager) {
        InputManager inputManager = stateManager.getApplication().getInputManager();

        addMouseMapping(inputManager, GameInputAction.MOUSE_LOOK, MouseInput.BUTTON_RIGHT);
        addMouseMapping(inputManager, GameInputAction.MOUSE_SELECT, MouseInput.BUTTON_LEFT);

        addKeyMapping(inputManager, GameInputAction.SELECT, KeyInput.KEY_RETURN);
        addKeyMapping(inputManager, GameInputAction.ORDER_ATTACK, KeyInput.KEY_NUMPAD7);
        addKeyMapping(inputManager, GameInputAction.ORDER_MOVE, KeyInput.KEY_NUMPAD8);
        addKeyMapping(inputManager, GameInputAction.ORDER_STOP, KeyInput.KEY_NUMPAD9);

        addKeyMapping(inputManager, GameInputAction.DEBUG_RELOAD_LEVEL, KeyInput.KEY_R);
        addKeyMapping(inputManager, GameInputAction.DEBUG_BUILD_1, KeyInput.KEY_1);
        addKeyMapping(inputManager, GameInputAction.DEBUG_BUILD_2, KeyInput.KEY_2);
        addKeyMapping(inputManager, GameInputAction.DEBUG_APPLY_FORCE, KeyInput.KEY_3);

    }

    private static void addMouseMapping(InputManager inputManager, GameInputAction action, int buttonCode) {
        inputManager.addMapping(action.trigger(), new MouseButtonTrigger(buttonCode));
    }

    private static void addKeyMapping(InputManager inputManager, GameInputAction action, int keyCode){
        inputManager.addMapping(action.trigger(), new KeyTrigger(keyCode));
    }
}
