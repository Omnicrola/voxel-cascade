package com.omnicrola.voxel.main.init;

import com.jme3.app.SimpleApplication;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.omnicrola.voxel.input.GameInputAction;

/**
 * Created by omnic on 2/28/2016.
 */
public class InputMappingLoader {
    public void createInputMappings(InputManager inputManager) {
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
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_PATHING, KeyInput.KEY_P);
    }

    private void addMouseMapping(InputManager inputManager, GameInputAction action, int buttonCode) {
        inputManager.addMapping(action.trigger(), new MouseButtonTrigger(buttonCode));
    }

    private void addKeyMapping(InputManager inputManager, GameInputAction action, int... keys) {
        for (int i = 0; i < keys.length; i++) {
            inputManager.addMapping(action.trigger(), new KeyTrigger(keys[i]));
        }
    }
}
