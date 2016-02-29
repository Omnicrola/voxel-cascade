package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerLoadLevelListener extends AbstractMessageListener<LoadLevelCommand, HostedConnection> {

    @Override
    protected void processMessage(HostedConnection connection, LoadLevelCommand message) {
        message.setIsLocal(true);
        connection.getServer().broadcast(message);
    }
}
