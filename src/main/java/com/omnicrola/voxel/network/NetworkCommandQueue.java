package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.omnicrola.voxel.commands.INetworkCommandQueue;
import com.omnicrola.voxel.commands.IWorldCommand;

import java.util.ArrayList;

/**
 * Created by omnic on 2/28/2016.
 */
public class NetworkCommandQueue implements INetworkCommandQueue {

    private final ArrayList<IWorldCommand> commands;

    public NetworkCommandQueue() {
        this.commands = new ArrayList<>();
    }

    @Override
    public synchronized void add(IWorldCommand worldCommand) {
        this.commands.add(worldCommand);
    }

    public synchronized void sendMessages(Client client) {
        this.commands.forEach(c -> client.send(c));
        this.commands.clear();
    }
}
