package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.network.AbstractMessageListener;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;
import com.omnicrola.voxel.server.network.RebroadcastFilter;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerLoadLevelListener extends AbstractMessageListener<LoadLevelMessage, HostedConnection> {

    @Override
    protected void processMessage(HostedConnection connection, LoadLevelMessage message) {
        connection.getServer().broadcast(new RebroadcastFilter(connection), message);
    }
}
