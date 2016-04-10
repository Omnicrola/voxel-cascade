package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.commands.SelectMultiplayerLevelCommand;
import com.omnicrola.voxel.network.AbstractMessageListener;

/**
 * Created by Eric on 4/10/2016.
 */
public class ServerSelectLevelListener extends AbstractMessageListener<SelectMultiplayerLevelCommand, HostedConnection> {
    @Override
    protected void processMessage(HostedConnection connection, SelectMultiplayerLevelCommand message) {
        message.setIsLocal(true);
        connection.getServer().broadcast(message);
    }
}
