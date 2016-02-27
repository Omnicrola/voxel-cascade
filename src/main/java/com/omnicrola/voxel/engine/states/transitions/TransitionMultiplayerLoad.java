package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionMultiplayerLoad implements IStateTransition {

    @Override
    public void run(Nifty niftyGui, AppStateManager stateManager) {
        niftyGui.gotoScreen(UiScreen.MULTIPLAYER_LOAD.toString());
    }
}
