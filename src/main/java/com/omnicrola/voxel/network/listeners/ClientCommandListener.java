package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.world.IWorldCommand;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientCommandListener extends AbstractMessageListener<IWorldCommand, Client> {
    private IActionQueue actionQueue;
    private WorldManagerState worldManagerState;

    public ClientCommandListener(WorldManagerState worldManagerState, IActionQueue actionQueue) {
        this.worldManagerState = worldManagerState;
        this.actionQueue = actionQueue;
    }

    @Override
    protected void processMessage(Client connection, IWorldCommand command) {
        this.actionQueue.enqueue(() -> addCommand(command));
    }

    private Object addCommand(IWorldCommand command) {
        this.worldManagerState.addCommand(command);
        return null;
    }
}
