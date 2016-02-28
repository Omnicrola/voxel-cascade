package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.listeners.ClientCommandListener;
import com.omnicrola.voxel.network.listeners.ClientHandshakeListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientListenerBuilder {
    private IActionQueue actionQueue;
    private ICommandProcessor commandProcessor;

    public ClientListenerBuilder(IActionQueue actionQueue,
                                 ICommandProcessor commandProcessor) {
        this.actionQueue = actionQueue;
        this.commandProcessor = commandProcessor;
    }

    public void attach(Client networkClient) {
        networkClient.addMessageListener(new ClientHandshakeListener(), HandshakeMessage.class);
        networkClient.addMessageListener(new ClientCommandListener(this.actionQueue, this.commandProcessor));
    }
}
