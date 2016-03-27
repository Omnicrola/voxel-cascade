package com.omnicrola.voxel.engine.states;

import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.GlobalGameState;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public interface IStateTransition {
    void enter(Nifty niftyGui, AppStateManager stateManager);

    void exit(Nifty niftyGui, AppStateManager stateManager);

    GlobalGameState transitionKey();
}
