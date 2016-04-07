package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;
import com.omnicrola.voxel.server.network.NetworkPlayer;
import com.omnicrola.voxel.server.network.ServerLobbyManager;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerJoinLobbyListener extends AbstractMessageListener<JoinLobbyMessage, HostedConnection> {
    private static final Logger LOGGER = Logger.getLogger(ServerJoinLobbyListener.class.getName());

    private ServerLobbyManager serverLobbyManager;

    public ServerJoinLobbyListener(ServerLobbyManager serverLobbyManager) {
        this.serverLobbyManager = serverLobbyManager;
    }

    @Override
    protected void processMessage(HostedConnection connection, JoinLobbyMessage message) {
        if (lobbyIsNotFull()) {
            LOGGER.log(Level.INFO, "Adding player from " + connection.getAddress());
            NetworkPlayer networkPlayer = new NetworkPlayer(connection);


            if (messageHasCorrectLobbyKey(message)) {
                LOGGER.log(Level.INFO, "Player at " + connection.getAddress() + " is host");
                networkPlayer.setIsHost(true);
            }
            this.serverLobbyManager.addPlayer(networkPlayer);
            message.joinWasSuccessful = true;
            connection.send(message);
        } else {
            LOGGER.log(Level.INFO, "Player from " + connection.getAddress() + " could not join, server is full.");
            message.joinWasSuccessful = false;
            connection.send(message);
        }
    }

    private boolean lobbyIsNotFull() {
        int playerCount = this.serverLobbyManager.getPlayerCount();
        int maxPlayers = this.serverLobbyManager.getMaxPlayers();
        return playerCount < maxPlayers;
    }

    private boolean messageHasCorrectLobbyKey(JoinLobbyMessage message) {
        return this.serverLobbyManager.getLobbyKey().equals(message.getLobbyKey());
    }
}
