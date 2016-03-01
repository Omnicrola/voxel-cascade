package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.debug.DebugState;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class DebugStateInitializer implements IStateInitializer {

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        WorldManager worldManager = initializationContainer.getWorldManager();
        return new DebugState(worldManager);
    }
}
