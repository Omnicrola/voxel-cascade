package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.world.IWorldMessage;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientCommandListener implements MessageListener<Client> {
    private IActionQueue actionQueue;
    private WorldManagerState worldManagerState;

    public ClientCommandListener(WorldManagerState worldManagerState, IActionQueue actionQueue) {
        this.worldManagerState = worldManagerState;
        this.actionQueue = actionQueue;
    }

    @Override
    public void messageReceived(Client client, Message message) {
        if (message instanceof IWorldMessage) {
            this.actionQueue.enqueue(() -> addCommand((IWorldMessage) message));
        }
    }

    private Object addCommand(IWorldMessage command) {
        System.out.println("C <= recieved message: " + command);
        this.worldManagerState.addCommand(command);
        return null;
    }

}
