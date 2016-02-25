package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.network.listeners.ClientCommandListener;
import com.omnicrola.voxel.network.listeners.ClientHandshakeListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientListenerBuilder {
    private IActionQueue actionQueue;
    private WorldManagerState worldManagerState;
    private ClientNetworkState clientNetworkState;

    public ClientListenerBuilder(IActionQueue actionQueue,
                                 WorldManagerState worldManagerState,
                                 ClientNetworkState clientNetworkState) {
        this.actionQueue = actionQueue;
        this.worldManagerState = worldManagerState;
        this.clientNetworkState = clientNetworkState;
    }

    public void attach(Client networkClient) {
        networkClient.addMessageListener(new ClientHandshakeListener(this.clientNetworkState), HandshakeMessage.class);
        networkClient.addMessageListener(new ClientCommandListener(this.worldManagerState, this.actionQueue));
    }
}
