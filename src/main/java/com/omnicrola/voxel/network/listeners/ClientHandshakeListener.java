package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.network.messages.HandshakeMessage;

/**
 * Created by Eric on 2/21/2016.
 */
public class ClientHandshakeListener extends AbstractMessageListener<HandshakeMessage, Client> {
    private ClientNetworkState clientNetworkState;

    public ClientHandshakeListener(ClientNetworkState clientNetworkState) {
        this.clientNetworkState = clientNetworkState;
    }

    @Override
    protected void processMessage(Client connection, HandshakeMessage message) {
        System.out.println("connected to server: " + connection.getGameName() + " " + connection.getId() + " " + connection.getVersion());
    }
}
