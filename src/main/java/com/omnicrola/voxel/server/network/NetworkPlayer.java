package com.omnicrola.voxel.server.network;

import com.jme3.network.HostedConnection;
import com.omnicrola.voxel.data.TeamId;

/**
 * Created by omnic on 3/25/2016.
 */
public class NetworkPlayer {
    private HostedConnection connection;
    private boolean isHost;
    private int teamId;

    public NetworkPlayer(HostedConnection connection) {
        this.connection = connection;
    }

    public void disconnect(String message) {
        connection.close(message);
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    public boolean isHost() {
        return isHost;
    }

    public HostedConnection getConnection() {
        return this.connection;
    }

    public int getId() {
        return this.connection.getId();
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public TeamId getTeamId() {
        return TeamId.create(this.teamId);
    }
}
