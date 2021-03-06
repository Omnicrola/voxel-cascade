package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.listeners.ClientChatMessageListener;
import com.omnicrola.voxel.network.listeners.ClientCommandListener;
import com.omnicrola.voxel.network.listeners.ClientHandshakeListener;
import com.omnicrola.voxel.network.listeners.ClientLobbyJoinMessageListener;
import com.omnicrola.voxel.network.messages.ChatMessage;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientListenerBuilder {
    private IActionQueue actionQueue;

    public ClientListenerBuilder(IActionQueue actionQueue) {
        this.actionQueue = actionQueue;
    }

    public void attach(Client networkClient, ICommandProcessor commandProcessor, INetworkManager networkManager) {
        networkClient.addMessageListener(new ClientHandshakeListener(), HandshakeMessage.class);
        networkClient.addMessageListener(new ClientLobbyJoinMessageListener(networkManager), JoinLobbyMessage.class);
        networkClient.addMessageListener(new ClientCommandListener(this.actionQueue, commandProcessor));
        networkClient.addMessageListener(new ClientChatMessageListener(commandProcessor), ChatMessage.class);
    }
}
