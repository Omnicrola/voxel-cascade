package com.omnicrola.voxel.debug;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * Created by Eric on 2/24/2016.
 */
public class ServerDebugMessageListener implements MessageListener<HostedConnection> {
    @Override
    public void messageReceived(HostedConnection hostedConnection, Message message) {
        System.out.println("S <= recieved : " + message);
    }
}
