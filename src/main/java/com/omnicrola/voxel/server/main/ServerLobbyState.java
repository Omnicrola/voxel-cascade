package com.omnicrola.voxel.server.main;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.network.BroadcastPacketParser;
import com.omnicrola.voxel.network.messages.StartGameMessage;
import com.omnicrola.voxel.server.network.*;

/**
 * Created by omnic on 3/25/2016.
 */
public class ServerLobbyState extends AbstractAppState {

    private ServerMulticastEmitter serverMulticastEmitter;
    private ServerLobbyManager serverLobbyManager;
    private VoxelServerEngine serverEngine;
    private VoxelNetworkServerFactory voxelNetworkServerFactory;

    public ServerLobbyState(VoxelNetworkServerFactory voxelNetworkServerFactory) {
        this.voxelNetworkServerFactory = voxelNetworkServerFactory;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.serverEngine = (VoxelServerEngine) app;
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
        if (this.serverMulticastEmitter != null) {
            this.serverMulticastEmitter.setIsRunning(false);
        }
    }

    private void enable() {
        this.serverMulticastEmitter = new ServerMulticastEmitter(new BroadcastPacketParser());
        INetworkServer server = this.voxelNetworkServerFactory.build();
        this.serverLobbyManager = new ServerLobbyManager(server);
        this.serverLobbyManager.startAcceptingPlayers(this, serverEngine);
        this.serverMulticastEmitter.start();
    }

    public void startGame() {
        this.serverMulticastEmitter.setIsRunning(false);
        this.serverMulticastEmitter = null;
        ActiveMultiplayerGame activeGame = this.serverLobbyManager.transitionToActiveGame();
        ServerActivePlayState activePlayState = this.serverEngine.getStateManager().getState(ServerActivePlayState.class);
        activePlayState.setCurrentGame(activeGame);

        activePlayState.setEnabled(true);
        this.setEnabled(false);

        activeGame.broadcast(new StartGameMessage());
    }
}
