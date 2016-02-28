package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.states.*;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionActivePlay implements IStateTransition {
    @Override
    public void run(Nifty niftyGui, AppStateManager stateManager) {
        stateManager.getState(MainMenuState.class).setEnabled(false);
        stateManager.getState(GameOverState.class).setEnabled(false);

        stateManager.getState(ActivePlayState.class).setEnabled(true);
        stateManager.getState(ShadowState.class).setEnabled(true);

        niftyGui.gotoScreen(UiScreen.ACTIVE_PLAY.toString());
    }
}
