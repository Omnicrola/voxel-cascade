package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/21/2016.
 */
public class ClientHandshakeListener extends AbstractMessageListener<HandshakeMessage, Client> {

    private static final Logger LOGGER = Logger.getLogger(ClientHandshakeListener.class.getName());

    @Override
    protected void processMessage(Client connection, HandshakeMessage message) {
        String msg = "Connected to server: \'{0}\' connectionId:{1} version:{2}";
        LOGGER.log(Level.INFO, MessageFormat.format(msg, connection.getGameName(), connection.getId(), connection.getVersion()));
    }
}
