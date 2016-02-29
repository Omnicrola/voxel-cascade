package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.engine.states.CommandState;

/**
 * Created by Eric on 2/28/2016.
 */
public class CommandStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        return new CommandState(worldCommandProcessor);
    }
}
