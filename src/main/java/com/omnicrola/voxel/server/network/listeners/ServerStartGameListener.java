package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.server.main.ServerLobbyState;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerStartGameListener extends AbstractMessageListener<StartMultiplayerGameCommand, HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerStartGameListener.class.getName());

    private ServerLobbyState serverLobbyState;
    private IActionQueue actionQueue;

    public ServerStartGameListener(ServerLobbyState serverLobbyState, IActionQueue actionQueue) {
        this.serverLobbyState = serverLobbyState;
        this.actionQueue = actionQueue;
    }

    @Override
    protected void processMessage(HostedConnection connection, StartMultiplayerGameCommand message) {
        connection.getServer().addMessageListener(new ServerCommandListener());

        this.actionQueue.enqueue(() -> {
            serverLobbyState.startGame();
            String msg = "Player from {0} has started the game!";
            LOGGER.log(Level.INFO, MessageFormat.format(msg, connection.getAddress()));
            message.setIsLocal(true);
            connection.getServer().broadcast(message);
            return null;
        });
    }
}
