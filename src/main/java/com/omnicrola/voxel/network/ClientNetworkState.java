package com.omnicrola.voxel.network;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.omnicrola.voxel.commands.IMessageProcessor;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.IWorldMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2/21/2016.
 */
public class ClientNetworkState extends AbstractAppState implements IMessageProcessor, INetworkManager {

    private Client networkClient;
    private ClientListenerBuilder listenerBuilder;
    private List<IWorldMessage> commands;
    private VoxelServerEngine voxelServerEngine;

    public ClientNetworkState() {
        this.commands = new ArrayList<>();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        WorldManagerState worldManager = stateManager.getState(WorldManagerState.class);
        this.listenerBuilder = new ClientListenerBuilder(voxelGameEngine, worldManager, this);
        setEnabled(true);
    }

    public void connect(String server) {
        connectTo(server);
    }

    public void disconnect() {
        this.networkClient.close();
    }

    @Deprecated
    public void message(AbstractMessage message) {
        if (this.networkClient != null) {
            this.networkClient.send(message);
        }
    }

    @Override
    public void sendLocal(IWorldMessage message) {
        this.commands.add(message);
    }


    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (this.networkClient != null) {
            this.commands.forEach(c -> {
                this.networkClient.send(c);
                System.out.println("C => sending " + c);
            });
            this.commands.clear();
        }
    }

    @Override
    public boolean connectTo(String serverAddress) {
        try {
            this.networkClient = Network.connectToServer(serverAddress, GameConstants.SERVER_PORT);
            this.listenerBuilder.attach(networkClient);
            this.networkClient.start();
            this.networkClient.send(new HandshakeMessage(GameConstants.GAME_VERSION));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void cleanup() {
        super.cleanup();
        if (this.voxelServerEngine != null) {
            this.voxelServerEngine.stop();
        }
    }

    @Override
    public void startMultiplayerServer() {
        this.voxelServerEngine = new VoxelServerEngine();
        this.voxelServerEngine.start();
    }
}
