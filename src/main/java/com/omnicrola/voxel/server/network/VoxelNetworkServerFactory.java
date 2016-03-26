package com.omnicrola.voxel.server.network;

import com.jme3.network.Network;
import com.jme3.network.Server;
import com.omnicrola.voxel.debug.ServerDebugMessageListener;
import com.omnicrola.voxel.network.MessageSerializationInitializer;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.server.network.listeners.ServerHandshakeListener;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 3/25/2016.
 */
public class VoxelNetworkServerFactory {

    private static final Logger LOGGER = Logger.getLogger(VoxelNetworkServerFactory.class.getName());

    public INetworkServer build() {
        try {
            LOGGER.log(Level.FINE, "Initializing JME network server");
            MessageSerializationInitializer.init();
            Server server = Network.createServer(GameConstants.SERVER_PORT);
            loadMessageListeners(server);
            return new VoxelNetworkServer(server);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
        return null;
    }

    private void loadMessageListeners(Server server) {
        server.addMessageListener(new ServerDebugMessageListener());
    }
}
