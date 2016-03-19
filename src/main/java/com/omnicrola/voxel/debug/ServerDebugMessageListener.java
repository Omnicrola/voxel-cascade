package com.omnicrola.voxel.debug;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/24/2016.
 */
public class ServerDebugMessageListener implements MessageListener<HostedConnection> {
    private static final Logger LOGGER = Logger.getLogger(ServerDebugMessageListener.class.getName());

    @Override
    public void messageReceived(HostedConnection hostedConnection, Message message) {
        LOGGER.log(Level.FINE, "S <= recieved : " + message);
    }
}
