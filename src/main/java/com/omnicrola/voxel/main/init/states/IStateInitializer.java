package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;

/**
 * Created by omnic on 2/28/2016.
 */
public interface IStateInitializer {
        AppState buildState(InitializationContainer initializationContainer);
}
