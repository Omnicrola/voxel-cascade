package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.server.network.ServerNetworkState;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerHandshakeListener extends AbstractMessageListener<HandshakeMessage, HostedConnection> {

    ServerNetworkState serverNetworkState;

    public ServerHandshakeListener(ServerNetworkState serverNetworkState) {
        this.serverNetworkState = serverNetworkState;
    }

    @Override
    protected void processMessage(HostedConnection connection, HandshakeMessage message) {
        String version = message.getVersion();
        if (version.equals(GameConstants.GAME_VERSION)) {
            connection.send(new HandshakeMessage(GameConstants.GAME_VERSION));
        } else {
            connection.close("Incompatible version. Required: " + GameConstants.GAME_VERSION);
        }
    }
}
