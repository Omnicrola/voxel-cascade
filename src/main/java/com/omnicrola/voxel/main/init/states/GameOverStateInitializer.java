package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.engine.states.GameOverState;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/28/2016.
 */
public class GameOverStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        Nifty niftyGui = initializationContainer.getNiftyGui();

        return new GameOverState(niftyGui);
    }
}
