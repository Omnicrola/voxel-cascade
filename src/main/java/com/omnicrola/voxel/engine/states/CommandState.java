package com.omnicrola.voxel.engine.states;

import com.jme3.app.state.AbstractAppState;
import com.omnicrola.voxel.commands.WorldCommandProcessor;

/**
 * Created by Eric on 2/28/2016.
 */
public class CommandState extends AbstractAppState {

    private WorldCommandProcessor worldCommandProcessor;

    public CommandState(WorldCommandProcessor worldCommandProcessor) {
        this.worldCommandProcessor = worldCommandProcessor;
    }

    @Override
    public void update(float tpf) {
        this.worldCommandProcessor.update();
    }
}
