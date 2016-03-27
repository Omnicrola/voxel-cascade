package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 3/27/2016.
 */
public class TransitionToMultiplayerLobby implements IStateTransition {
    @Override
    public void enter(Nifty niftyGui, AppStateManager stateManager) {
        niftyGui.gotoScreen(UiScreen.MULTIPLAYER_LOBBY.toString());
    }

    @Override
    public void exit(Nifty niftyGui, AppStateManager stateManager) {

    }

    @Override
    public GlobalGameState transitionKey() {
        return GlobalGameState.MULTIPLAYER_LOBBY;
    }
}
