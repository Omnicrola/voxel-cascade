package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.omnicrola.voxel.engine.states.GameOverState;
import com.omnicrola.voxel.engine.states.transitions.TransitionMainMenu;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/28/2016.
 */
public class GameOverStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        InputManager inputManager = initializationContainer.getInputManager();
        Nifty niftyGui = initializationContainer.getNiftyGui();
        AppStateManager stateManager = initializationContainer.getStateManager();

        TransitionMainMenu transitionMainMenu = new TransitionMainMenu();

        return new GameOverState(inputManager, niftyGui, transitionMainMenu, stateManager);
    }
}
