package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Server;

/**
 * Created by omnic on 3/25/2016.
 */
public class VoxelNetworkServer implements INetworkServer {
    private Server networkServer;

    public VoxelNetworkServer(Server server) {
        this.networkServer = server;
    }

    @Override
    public void addMessageListener(MessageListener<? super HostedConnection> listener, Class... messageClasses) {
        this.networkServer.addMessageListener(listener, messageClasses);
    }

    @Override
    public void removeMessageListener(MessageListener l) {

    }

    @Override
    public void broadcast(Message message) {
        this.networkServer.broadcast(message);
    }

    @Override
    public void stop() {
        this.networkServer.close();
    }
}
