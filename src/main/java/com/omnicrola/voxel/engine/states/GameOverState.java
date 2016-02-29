package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.InputManager;
import com.omnicrola.voxel.engine.states.transitions.TransitionMainMenu;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends AbstractAppState {

    private InputManager inputManager;
    private Nifty niftyGui;
    private TransitionMainMenu transitionMainMenu;
    private AppStateManager stateManager;

    public GameOverState(InputManager inputManager, Nifty niftyGui, TransitionMainMenu transitionMainMenu, AppStateManager stateManager) {
        this.inputManager = inputManager;
        this.niftyGui = niftyGui;
        this.transitionMainMenu = transitionMainMenu;
        this.stateManager = stateManager;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.setEnabled(false);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            this.niftyGui.gotoScreen(UiScreen.GAME_OVER.toString());
        } else {
//            this.transitionMainMenu.run(this.niftyGui, this.stateManager);
        }
    }
}
