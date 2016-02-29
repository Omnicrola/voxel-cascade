package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.omnicrola.voxel.commands.IWorldCommand;

/**
 * Created by Eric on 2/28/2016.
 */
public class ServerCommandListener implements MessageListener<HostedConnection> {
    @Override
    public void messageReceived(HostedConnection hostedConnection, Message message) {
        if (message instanceof IWorldCommand) {
            IWorldCommand worldCommand = (IWorldCommand) message;
            worldCommand.setIsLocal(true);
            hostedConnection.getServer().broadcast(worldCommand);
        }
    }
}
