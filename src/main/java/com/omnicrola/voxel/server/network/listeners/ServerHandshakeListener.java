package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.server.network.ServerNetworkState;
import com.omnicrola.voxel.settings.GameConstants;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerHandshakeListener extends AbstractMessageListener<HandshakeMessage, HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerHandshakeListener.class.getName());

    ServerNetworkState serverNetworkState;

    public ServerHandshakeListener(ServerNetworkState serverNetworkState) {
        this.serverNetworkState = serverNetworkState;
    }

    @Override
    protected void processMessage(HostedConnection connection, HandshakeMessage message) {
        String version = message.getVersion();
        if (version.equals(GameConstants.GAME_VERSION)) {
            connection.send(new HandshakeMessage(GameConstants.GAME_VERSION));
            LOGGER.log(Level.INFO, "Client connected: " + connection.getId() + " " + connection.getAddress());
        } else {
            String msg = "Client attempted to correct with incorrect version. Need {0} but was {1}";
            LOGGER.log(Level.WARNING, MessageFormat.format(msg, GameConstants.GAME_VERSION, version));
        }
    }
}
