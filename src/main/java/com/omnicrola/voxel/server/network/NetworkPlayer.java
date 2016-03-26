package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;

/**
 * Created by omnic on 3/25/2016.
 */
public class NetworkPlayer {
    private HostedConnection connection;

    public NetworkPlayer(HostedConnection connection) {
        this.connection = connection;
    }

    public void disconnect(String message) {
        connection.close(message);
    }
}
