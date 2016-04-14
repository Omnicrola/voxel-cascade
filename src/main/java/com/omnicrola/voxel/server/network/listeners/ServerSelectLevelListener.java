package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import com.omnicrola.voxel.commands.SelectMultiplayerLevelCommand;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.ChatMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 4/10/2016.
 */
public class ServerSelectLevelListener extends AbstractMessageListener<SelectMultiplayerLevelCommand, HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerSelectLevelListener.class.getName());

    @Override
    protected void processMessage(HostedConnection connection, SelectMultiplayerLevelCommand message) {
        message.setIsLocal(true);
        Server server = connection.getServer();

        server.broadcast(message);

        String time = String.valueOf(System.currentTimeMillis());
        String sender = "Server";
        connection.send(new ChatMessage(time, sender, "Changed level to " + message.getLevelId()));

        LOGGER.log(Level.INFO, "Changed level : " + message.getLevelId());
    }
}
