package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionMainMenu implements IStateTransition {
    @Override
    public void enter(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(MainMenuState.class).setEnabled(true);
        niftyGui.gotoScreen(UiScreen.MAIN_MENU.toString());
    }

    @Override
    public void exit(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(MainMenuState.class).setEnabled(false);
    }

    @Override
    public GlobalGameState transitionKey() {
        return GlobalGameState.MAIN_MENU;
    }
}
