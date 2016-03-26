package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.StartGameMessage;
import com.omnicrola.voxel.server.main.ServerLobbyState;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerStartGameListener extends AbstractMessageListener<StartGameMessage, HostedConnection> {
    private ServerLobbyState serverLobbyState;
    private IActionQueue actionQueue;

    public ServerStartGameListener(ServerLobbyState serverLobbyState, IActionQueue actionQueue) {
        this.serverLobbyState = serverLobbyState;
        this.actionQueue = actionQueue;
    }

    @Override
    protected void processMessage(HostedConnection connection, StartGameMessage message) {
        this.actionQueue.enqueue(() -> {
            serverLobbyState.startGame();
            return null;
        });
    }
}
