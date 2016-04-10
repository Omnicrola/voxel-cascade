package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.server.main.ServerLobbyState;
import com.omnicrola.voxel.server.network.NetworkPlayer;
import com.omnicrola.voxel.server.network.ServerLobbyManager;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerStartGameListener extends AbstractMessageListener<StartMultiplayerGameCommand, HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerStartGameListener.class.getName());

    private ServerLobbyManager serverLobbyManager;
    private ServerLobbyState serverLobbyState;
    private IActionQueue actionQueue;

    public ServerStartGameListener(ServerLobbyManager serverLobbyManager,
                                   ServerLobbyState serverLobbyState,
                                   IActionQueue actionQueue) {
        this.serverLobbyManager = serverLobbyManager;
        this.serverLobbyState = serverLobbyState;
        this.actionQueue = actionQueue;
    }

    @Override
    protected void processMessage(HostedConnection connection, StartMultiplayerGameCommand message) {
        if (this.serverLobbyManager.isHost(connection)) {
            startGame(connection, message);
        } else {
            LOGGER.log(Level.FINE, "Player from " + connection.getAddress() + " tried to start game, but is not the host.");
        }
    }

    private void startGame(HostedConnection connection, StartMultiplayerGameCommand message) {
        connection.getServer().addMessageListener(new ServerCommandListener());

        this.actionQueue.enqueue(() -> {
            serverLobbyState.startGame();
            String msg = "Player from {0} has started the game!";
            LOGGER.log(Level.INFO, MessageFormat.format(msg, connection.getAddress()));
            message.setIsLocal(true);
            List<NetworkPlayer> players = serverLobbyManager.getPlayers();

            List<TeamId> allPlayers = players.stream().map(p -> p.getConnection().getId()).map(i -> TeamId.create(i)).collect(Collectors.toList());
            players.forEach(player -> {
                int playerId = player.getConnection().getId();
                StartMultiplayerGameCommand command = new StartMultiplayerGameCommand(message, TeamId.create(playerId), allPlayers);
                player.getConnection().send(command);
            });
            return null;
        });
    }
}
