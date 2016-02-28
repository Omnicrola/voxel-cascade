package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;

/**
 * Created by omnic on 2/28/2016.
 */
public class NetworkManager implements INetworkManager {

    private Client networkClient;
    private VoxelServerEngine voxelServerEngine;
    private ClientListenerBuilder clientListenerBuilder;
    private NetworkCommandQueue networkCommandQueue;

    public NetworkManager(ClientListenerBuilder clientListenerBuilder, NetworkCommandQueue networkCommandQueue) {
        this.clientListenerBuilder = clientListenerBuilder;
        this.networkCommandQueue = networkCommandQueue;
    }

    @Override
    public void disconnect() {
        if (this.networkClient != null) {
            this.networkClient.close();
            this.networkClient = null;
        }
    }

    @Override
    public boolean connectTo(String serverAddress) {
        try {
            this.networkClient = Network.connectToServer(serverAddress, GameConstants.SERVER_PORT);
            this.clientListenerBuilder.attach(networkClient);
            this.networkClient.start();
            this.networkClient.send(new HandshakeMessage(GameConstants.GAME_VERSION));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void startMultiplayerServer() {
        this.voxelServerEngine = new VoxelServerEngine();
        this.voxelServerEngine.enqueue(() -> {
            System.out.println("Multiplayer Start!");
            return null;
        });
        this.voxelServerEngine.start();
        connectTo("localhost");
    }

    @Override
    public void shutdownMultiplayer() {
        if (this.voxelServerEngine != null) {
            this.voxelServerEngine.stop();
            this.voxelServerEngine = null;
        }
    }

    public void cleanup() {
        disconnect();
        shutdownMultiplayer();
    }

    public void update(float tpf) {
        if (this.networkClient != null) {
            this.networkCommandQueue.sendMessages(this.networkClient);
        }
    }
}
