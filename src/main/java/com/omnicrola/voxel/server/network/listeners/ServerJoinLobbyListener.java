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
        LOGGER.log(Level.INFO, "Adding player from " + connection.getAddress());
        this.serverLobbyManager.addPlayer(new NetworkPlayer(connection));
    }
}
