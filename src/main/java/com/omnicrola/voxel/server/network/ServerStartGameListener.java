package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.StartGameMessage;
import com.omnicrola.voxel.server.main.ServerLobbyState;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerStartGameListener extends AbstractMessageListener<StartGameMessage, HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerStartGameListener.class.getName());

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
            String msg = "Player from {0} has started the game!";
            LOGGER.log(Level.INFO, MessageFormat.format(msg, connection.getAddress()));
            return null;
        });
    }
}
