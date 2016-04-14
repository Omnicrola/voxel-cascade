package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.ChatMessage;
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
        if (playerIsHost(connection)) {
            if (allPlayersHaveChosenATeam()) {
                startGame(connection, message);
            } else {
                sendChatMessage(connection, "Not all players have chosen a team!");
                LOGGER.log(Level.FINE, "Cannot start game, not all players are ready.");
            }
        } else {
            sendChatMessage(connection, "You are not the host");
            LOGGER.log(Level.FINE, "Player from " + connection.getAddress() + " tried to start game, but is not the host.");
        }
    }

    private void sendChatMessage(HostedConnection connection, String chatContent) {
        String time = String.valueOf(System.currentTimeMillis());
        String sender = "Server";
        connection.send(new ChatMessage(time, sender, chatContent));
    }

    private boolean allPlayersHaveChosenATeam() {
        return this.serverLobbyManager.allPlayersHaveTeams() &&
                this.serverLobbyManager.allPlayersHaveDifferentTeams();
    }

    private boolean playerIsHost(HostedConnection connection) {
        return this.serverLobbyManager.isHost(connection);
    }

    private void startGame(HostedConnection connection, StartMultiplayerGameCommand message) {
        connection.getServer().addMessageListener(new ServerCommandListener());

        this.actionQueue.enqueue(() -> {
            serverLobbyState.startGame();
            String msg = "Player from {0} has started the game!";
            LOGGER.log(Level.INFO, MessageFormat.format(msg, connection.getAddress()));
            message.setIsLocal(true);
            List<NetworkPlayer> players = serverLobbyManager.getPlayers();

            List<TeamId> allTeams = players.stream().map(p -> p.getTeamId()).collect(Collectors.toList());
            players.forEach(player -> {
                StartMultiplayerGameCommand command = new StartMultiplayerGameCommand(message, player.getTeamId(), allTeams);
                player.getConnection().send(command);
            });
            return null;
        });
    }
}
