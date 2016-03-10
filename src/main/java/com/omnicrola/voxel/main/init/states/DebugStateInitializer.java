package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.debug.DebugState;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 2/28/2016.
 */
public class DebugStateInitializer implements IStateInitializer {

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        WorldManager worldManager = initializationContainer.getWorldManager();
        WorldEntityBuilder worldEntityBuilder = initializationContainer.getWorldEntityBuilder();
        return new DebugState(worldManager, worldEntityBuilder);
    }
}
