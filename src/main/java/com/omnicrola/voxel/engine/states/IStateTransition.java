package com.omnicrola.voxel.engine.states;

import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/27/2016.
 */
public interface IStateTransition {
    void run(Nifty niftyGui, AppStateManager stateManager);
}
