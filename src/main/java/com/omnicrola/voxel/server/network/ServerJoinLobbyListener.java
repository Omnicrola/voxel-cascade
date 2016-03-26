package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerJoinLobbyListener extends AbstractMessageListener<JoinLobbyMessage, HostedConnection> {
    private ServerLobbyManager serverLobbyManager;

    public ServerJoinLobbyListener(ServerLobbyManager serverLobbyManager) {
        this.serverLobbyManager = serverLobbyManager;
    }

    @Override
    protected void processMessage(HostedConnection connection, JoinLobbyMessage message) {
        this.serverLobbyManager.addPlayer(new NetworkPlayer(connection));
    }
}
