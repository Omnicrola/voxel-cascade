package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * Created by omnic on 3/25/2016.
 */
public interface INetworkServer {
    void addMessageListener(MessageListener<? super HostedConnection> listener, Class... messageClasses);

    void removeMessageListener(MessageListener l);

    void broadcast(Message message);

    void stop();

    void start();
}
