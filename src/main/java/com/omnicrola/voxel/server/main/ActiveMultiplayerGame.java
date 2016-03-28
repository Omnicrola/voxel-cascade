package com.omnicrola.voxel.server.main;

import com.jme3.network.Message;
import com.omnicrola.voxel.server.network.INetworkServer;
import com.omnicrola.voxel.server.network.NetworkPlayer;

import java.util.List;

/**
 * Created by omnic on 3/25/2016.
 */
public class ActiveMultiplayerGame {
    private INetworkServer server;
    private List<NetworkPlayer> players;

    public ActiveMultiplayerGame(INetworkServer server, List<NetworkPlayer> players) {
        this.server = server;
        this.players = players;
    }

    public void broadcast(Message message) {
        this.server.broadcast(message);
    }

    public void cleanup() {
        this.players.forEach(p -> p.disconnect("Server closing"));
        server.stop();
    }
}
