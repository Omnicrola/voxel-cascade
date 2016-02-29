package com.omnicrola.voxel.server.network;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.omnicrola.voxel.debug.ServerDebugMessageListener;
import com.omnicrola.voxel.network.MessageSerializationInitializer;
import com.omnicrola.voxel.network.messages.HandshakeMessage;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
import com.omnicrola.voxel.server.main.VoxelServerEngine;
import com.omnicrola.voxel.server.network.listeners.ServerHandshakeListener;
import com.omnicrola.voxel.server.network.listeners.ServerLoadLevelListener;
import com.omnicrola.voxel.settings.GameConstants;

import java.io.IOException;

/**
 * Created by Eric on 2/21/2016.
 */
public class ServerNetworkState extends AbstractAppState {

    private Server networkServer;
    private VoxelServerEngine game;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.game = (VoxelServerEngine) app;
        this.networkServer = createServer();
        this.setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    private void disable() {
        this.networkServer.close();
    }

    private void enable() {
        this.networkServer.start();
    }

    private Server createServer() {
        try {
            MessageSerializationInitializer.init();
            Server server = Network.createServer(GameConstants.SERVER_PORT);
            loadMessageListeners(server);
            return server;
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }

    private void loadMessageListeners(Server server) {
        server.addMessageListener(new ServerDebugMessageListener(), HandshakeMessage.class, LoadLevelCommand.class);
        server.addMessageListener(new ServerHandshakeListener(this), HandshakeMessage.class);
        server.addMessageListener(new ServerLoadLevelListener(), LoadLevelCommand.class);
    }
}
