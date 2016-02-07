package com.omnicrola.voxel.main.init;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.debug.DebugState;
import com.omnicrola.voxel.engine.states.*;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameGui;
import com.omnicrola.voxel.ui.builders.ActivePlayUiBuilder;
import com.omnicrola.voxel.ui.builders.GameOverUiBuilder;
import com.omnicrola.voxel.ui.builders.MainMenuUiBuilder;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {
    public static void initializeGame(IGameContainer gameContainer, InputManager inputManager) {
        createStates(gameContainer);
        createGui(gameContainer);
        createInputMappings(inputManager);
    }

    private static void createStates(IGameContainer stateManager) {
        DebugState debugState = new DebugState();
        LoadingState loadingState = new LoadingState();
        CurrentLevelState currentLevelState = new CurrentLevelState(new GameXmlDataParser());
        ActivePlayInputState playState = new ActivePlayInputState();
        MainMenuState mainMenuState = new MainMenuState();
        GameOverState gameOverState = new GameOverState();

        stateManager.addState(debugState);
        stateManager.addState(loadingState);
        stateManager.addState(currentLevelState);
        stateManager.addState(mainMenuState);
        stateManager.addState(playState);
        stateManager.addState(gameOverState);
    }

    private static void createGui(IGameContainer gameContainer) {
        CurrentLevelState currentLevelState = gameContainer.getState(CurrentLevelState.class);
        IGameGui gameGui = gameContainer.gui();
        ActivePlayUiBuilder.build(gameGui, currentLevelState);

        GameOverUiBuilder.build(gameGui, currentLevelState);
        MainMenuUiBuilder.build(gameContainer);
    }

    private static void createInputMappings(InputManager inputManager) {
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        addMouseMapping(inputManager, GameInputAction.MOUSE_SECONDARY, MouseInput.BUTTON_RIGHT);
        addMouseMapping(inputManager, GameInputAction.MOUSE_PRIMARY, MouseInput.BUTTON_LEFT);
        inputManager.addMapping(GameInputAction.MOUSE_MOVEMENT.toString(),
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, true),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));

        addKeyMapping(inputManager, GameInputAction.SELECT, KeyInput.KEY_RETURN);
        addKeyMapping(inputManager, GameInputAction.CLEAR_SELECTION, KeyInput.KEY_ESCAPE);
        addKeyMapping(inputManager, GameInputAction.MULTI_SELECT, KeyInput.KEY_LSHIFT, KeyInput.KEY_RSHIFT);

        addKeyMapping(inputManager, GameInputAction.CAMERA_FORWARD, KeyInput.KEY_UP);
        addKeyMapping(inputManager, GameInputAction.CAMERA_BACKWARD, KeyInput.KEY_DOWN);
        addKeyMapping(inputManager, GameInputAction.CAMERA_LEFT, KeyInput.KEY_LEFT);
        addKeyMapping(inputManager, GameInputAction.CAMERA_RIGHT, KeyInput.KEY_RIGHT);

        addKeyMapping(inputManager, GameInputAction.TOGGLE_DEBUG_MODE, KeyInput.KEY_F3);
        addKeyMapping(inputManager, GameInputAction.DEBUG_RELOAD_LEVEL, KeyInput.KEY_R);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_MOUSE_LOOK, KeyInput.KEY_TAB);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_PHYSICS, KeyInput.KEY_1);
        addKeyMapping(inputManager, GameInputAction.DEBUG_REBUILD_TERRAIN, KeyInput.KEY_2);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_WIREFRAME, KeyInput.KEY_4);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TARGET_OBJECT, KeyInput.KEY_T);
        addKeyMapping(inputManager, GameInputAction.DEBUG_SCENE_GRAPH, KeyInput.KEY_G);
    }

    private static void addMouseMapping(InputManager inputManager, GameInputAction action, int buttonCode) {
        inputManager.addMapping(action.trigger(), new MouseButtonTrigger(buttonCode));
    }

    private static void addKeyMapping(InputManager inputManager, GameInputAction action, int... keys) {
        for (int i = 0; i < keys.length; i++) {
            inputManager.addMapping(action.trigger(), new KeyTrigger(keys[i]));
        }
    }
}
