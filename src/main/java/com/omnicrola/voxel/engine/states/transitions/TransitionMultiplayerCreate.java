package com.omnicrola.voxel.engine.states.transitions;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.states.IStateTransition;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.ui.UiScreen;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public class TransitionMultiplayerCreate implements IStateTransition {

    @Override
    public void enter(Nifty niftyGui, AppStateManager stateManager) {
        NetworkManager networkManager = stateManager.getState(ClientNetworkState.class).getNetworkManager();
        networkManager.startLocalMultiplayerServer();
        niftyGui.gotoScreen(UiScreen.MULTIPLAYER_CREATE.toString());
    }

    @Override
    public void exit(Nifty niftyGui, AppStateManager stateManager) {
        NetworkManager networkManager = stateManager.getState(ClientNetworkState.class).getNetworkManager();
        networkManager.closeLocalMultiplayerServer();
    }

    @Override
    public GlobalGameState transitionKey() {
        return GlobalGameState.MULTIPLAYER_CREATE;
    }
}
