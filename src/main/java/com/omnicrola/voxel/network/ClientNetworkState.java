package com.omnicrola.voxel.network;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.AbstractMessage;
import com.jme3.network.Client;
import com.jme3.network.Network;
import com.omnicrola.voxel.network.listeners.ClientHandshakeListener;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;

/**
 * Created by Eric on 2/21/2016.
 */
public class ClientNetworkState extends AbstractAppState {

    private Client networkClient;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        setEnabled(true);
    }

    public void connect(String server) {
        try {
            this.networkClient = Network.connectToServer(server, GameConstants.SERVER_PORT);
            addListeners(this.networkClient);
            this.networkClient.start();
            this.networkClient.send(new HandshakeMessage(GameConstants.GAME_VERSION));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addListeners(Client networkClient) {
        networkClient.addMessageListener(new ClientHandshakeListener(this), HandshakeMessage.class);
    }

    public void disconnect() {
        this.networkClient.close();
    }

    public void message(AbstractMessage message) {
        if (this.networkClient != null) {
            this.networkClient.send(message);
        }
    }
}
