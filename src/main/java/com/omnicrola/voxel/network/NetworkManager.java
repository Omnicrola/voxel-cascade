package com.omnicrola.voxel.network;

import com.jme3.network.Client;
import com.jme3.network.Network;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.JoinLobbyMessage;
import com.omnicrola.voxel.server.main.ServerLobbyState;
import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by omnic on 2/28/2016.
 */
public class NetworkManager implements INetworkManager {

    private static final Logger LOGGER = Logger.getLogger(NetworkManager.class.getName());
    private static final UUID CLIENT_ID = UUID.randomUUID();

    private Client networkClient;
    private VoxelServerEngine voxelServerEngine;

    private ClientListenerBuilder clientListenerBuilder;
    private NetworkCommandQueue networkCommandQueue;
    private MultiplayerDiscovery multiplayerDiscovery;
    private WorldCommandProcessor commandProcessor;
    private List<INetworkObserver> observers;
    private VoxelGameServer currentGame;

    public NetworkManager(ClientListenerBuilder clientListenerBuilder,
                          NetworkCommandQueue networkCommandQueue,
                          MultiplayerDiscovery multiplayerDiscovery) {
        MessageSerializationInitializer.init();
        this.clientListenerBuilder = clientListenerBuilder;
        this.networkCommandQueue = networkCommandQueue;
        this.multiplayerDiscovery = multiplayerDiscovery;
        this.observers = new ArrayList<>();
    }

    @Override
    public void disconnect() {
        if (this.networkClient != null) {
            LOGGER.log(Level.INFO, "Disconnecting from server...");
            this.networkClient.close();
            this.networkClient = null;
        }
    }

    @Override
    public boolean joinLobby(VoxelGameServer multiplayerServer) {
        if (connectTo(multiplayerServer.getAddress())) {
            this.currentGame = multiplayerServer;
            this.observers.forEach(o -> o.multiplayerGameChanged(multiplayerServer));
            return true;
        }
        return false;
    }

    @Override
    public VoxelGameServer getCurrentServer() {
        return this.currentGame;
    }

    private boolean connectTo(String serverAddress) {
        try {
            LOGGER.log(Level.INFO, "Connecting to server : " + serverAddress + ":" + GameConstants.SERVER_PORT);
            this.networkClient = Network.connectToServer(GameConstants.GAME_NAME, GameConstants.GAME_VERSION, serverAddress, GameConstants.SERVER_PORT);
            LOGGER.log(Level.FINE, "Loading network listeners..");
            this.clientListenerBuilder.attach(networkClient, this.commandProcessor);
            LOGGER.log(Level.FINE, "Starting network client...");
            this.networkClient.start();
            LOGGER.log(Level.FINE, "Sending handshake message...");
            this.networkClient.send(new HandshakeMessage(GameConstants.GAME_VERSION));
            LOGGER.log(Level.FINE, "Sending join lobby message...");
            this.networkClient.send(new JoinLobbyMessage(CLIENT_ID));
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Could not connect to server : " + e.getMessage());
            return false;
        }
    }

    @Override
    public void startLocalMultiplayerServer() {
        LOGGER.log(Level.INFO, "Starting local multiplayer instance");
        this.voxelServerEngine = new VoxelServerEngine();
        this.voxelServerEngine.enqueue(() -> {
            LOGGER.log(Level.INFO, "Multiplayer server is running.");
            ServerLobbyState lobbyState = voxelServerEngine.getStateManager().getState(ServerLobbyState.class);
            lobbyState.setLobbyKey(CLIENT_ID);
            return null;
        });
        this.voxelServerEngine.start();
        attemptToJoinLocalhost();
    }

    private void attemptToJoinLocalhost() {
        try {
            VoxelGameServer localhost = new VoxelGameServer(InetAddress.getByName("localhost"), 1);
            if (joinLobby(localhost)) {
                LOGGER.log(Level.INFO, "Joined local server");
            } else {
                attemptToJoinLocalhost();
            }
        } catch (UnknownHostException e) {
            LOGGER.log(Level.SEVERE, null, e);
        }
    }

    @Override
    public void stopLocalMultiplayerServer() {
        if (this.voxelServerEngine != null) {
            LOGGER.log(Level.INFO, "Closing multiplayer server");
            this.voxelServerEngine.stop();
            this.voxelServerEngine = null;
        }
        this.multiplayerDiscovery.cleanup();
    }

    public void cleanup() {
        disconnect();
        stopLocalMultiplayerServer();
    }

    public void update(float tpf) {
        if (this.networkClient != null) {
            this.networkCommandQueue.sendMessages(this.networkClient);
        }
        if (this.multiplayerDiscovery.hasNewServers()) {
            List<VoxelGameServer> multiplayerGames = Collections.unmodifiableList(multiplayerDiscovery.getServers());
            this.observers.forEach(o -> o.availableServersChanged(multiplayerGames));
        }
    }

    public void setCommandProcessor(WorldCommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void startListeningForServers() {
        this.multiplayerDiscovery.startSearching();
    }

    @Override
    public void stopListeningForServers() {
        this.multiplayerDiscovery.stopSearching();
    }

    @Override
    public void addObserver(INetworkObserver networkObserver) {
        this.observers.add(networkObserver);
    }

    @Override
    public void removeObserver(INetworkObserver observer) {
        this.observers.remove(observer);
    }
}
