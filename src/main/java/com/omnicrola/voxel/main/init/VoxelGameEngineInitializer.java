package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.engine.states.LoadingState;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.input.GameInputAction;

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
        ActivePlayState playState = new ActivePlayState(new GameXmlDataParser());
        MainMenuState mainMenuState = new MainMenuState(playState);

        stateManager.attach(loadingState);
        stateManager.attach(mainMenuState);
        stateManager.attach(playState);
    }

    private static void createInputMappings(AppStateManager stateManager) {
        InputManager inputManager = stateManager.getApplication().getInputManager();

        addMouseMapping(inputManager, GameInputAction.MOUSE_SECONDARY, MouseInput.BUTTON_RIGHT);
        addMouseMapping(inputManager, GameInputAction.MOUSE_PRIMARY, MouseInput.BUTTON_LEFT);

        addKeyMapping(inputManager, GameInputAction.SELECT, KeyInput.KEY_RETURN);
        addKeyMapping(inputManager, GameInputAction.CLEAR_SELECTION, KeyInput.KEY_ESCAPE);

        addKeyMapping(inputManager, GameInputAction.ORDER_MOVE, KeyInput.KEY_1);
        addKeyMapping(inputManager, GameInputAction.ORDER_ATTACK, KeyInput.KEY_2);
        addKeyMapping(inputManager, GameInputAction.ORDER_STOP, KeyInput.KEY_3);
        addKeyMapping(inputManager, GameInputAction.ORDER_BUILD_MODE, KeyInput.KEY_B);
        addKeyMapping(inputManager, GameInputAction.ORDER_BUILD_SELECT_1, KeyInput.KEY_Q);
        addKeyMapping(inputManager, GameInputAction.ORDER_BUILD_SELECT_2, KeyInput.KEY_W);

        addKeyMapping(inputManager, GameInputAction.TOGGLE_PHYSICS_DEBUG, KeyInput.KEY_F3);
        addKeyMapping(inputManager, GameInputAction.DEBUG_RELOAD_LEVEL, KeyInput.KEY_R);
    }

    private static void addMouseMapping(InputManager inputManager, GameInputAction action, int buttonCode) {
        inputManager.addMapping(action.trigger(), new MouseButtonTrigger(buttonCode));
    }

    private static void addKeyMapping(InputManager inputManager, GameInputAction action, int keyCode) {
        inputManager.addMapping(action.trigger(), new KeyTrigger(keyCode));
    }
}
