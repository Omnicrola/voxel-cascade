package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.engine.states.ShadowState;

/**
 * Created by omnic on 2/28/2016.
 */
public class ShadowStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        return new ShadowState();
    }
}
