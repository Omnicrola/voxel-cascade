package com.omnicrola.voxel.server.network.listeners;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.omnicrola.voxel.commands.IEntityCreator;
import com.omnicrola.voxel.commands.IWorldCommand;
import com.omnicrola.voxel.server.network.ServerInstanceIdProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/28/2016.
 */
public class ServerCommandListener implements MessageListener<HostedConnection> {

    private static final Logger LOGGER = Logger.getLogger(ServerCommandListener.class.getName());

    @Override
    public void messageReceived(HostedConnection hostedConnection, Message message) {
        if (message instanceof IWorldCommand) {
            LOGGER.log(Level.FINE, "Server recieved command : " + message.getClass().getSimpleName());
            IWorldCommand worldCommand = (IWorldCommand) message;
            worldCommand.setIsLocal(true);
            handleInstanceIds(worldCommand);
            hostedConnection.getServer().broadcast(worldCommand);
        }
    }

    private void handleInstanceIds(IWorldCommand worldCommand) {
        if (worldCommand instanceof IEntityCreator) {
            int id = ServerInstanceIdProvider.INSTANCE.getNextId();
            ((IEntityCreator) worldCommand).setInstanceId(id);
        }
    }
}
