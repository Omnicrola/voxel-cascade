package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.*;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionActivePlay implements IStateTransition {
    @Override
    public void enter(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(GameOverState.class).setEnabled(false);
        stateManager.getState(ActivePlayState.class).setEnabled(true);
        stateManager.getState(ShadowState.class).setEnabled(true);

        niftyGui.gotoScreen(UiScreen.ACTIVE_PLAY.toString());
    }

    @Override
    public void exit(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(GameOverState.class).setEnabled(false);
        stateManager.getState(ActivePlayState.class).setEnabled(false);
        stateManager.getState(ShadowState.class).setEnabled(false);
    }

    @Override
    public GlobalGameState transitionKey() {
        return GlobalGameState.ACTIVE_PLAY;
    }
}
