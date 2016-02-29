package com.omnicrola.voxel.jme.wrappers.impl;

import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.IGameNetwork;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.network.messages.LoadLevelCommand;
import com.omnicrola.voxel.server.main.init.ServerBootstrapper;
import com.omnicrola.voxel.server.main.init.VoxelServerLauncher;

import java.util.UUID;

/**
 * Created by Eric on 2/21/2016.
 */
public class JmeNetworkWrapper implements IGameNetwork {
    private VoxelGameEngine game;
    private VoxelServerLauncher serverLauncher;

    public JmeNetworkWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void connectTo(String servername) {
        ClientNetworkState clientNetworkState = this.game.getStateManager().getState(ClientNetworkState.class);
//        clientNetworkState.connect(servername);
    }

    @Override
    public void loadLevel(UUID levelId) {
        ClientNetworkState clientNetworkState = this.game.getStateManager().getState(ClientNetworkState.class);
        LoadLevelCommand loadLevelCommand = new LoadLevelCommand();
//        clientNetworkState.message(loadLevelCommand);
    }

    @Override
    public void closeConnection() {
        ClientNetworkState clientNetworkState = this.game.getStateManager().getState(ClientNetworkState.class);
//        clientNetworkState.disconnect();
    }

    @Override
    public void startLocalServer() {
        if (this.serverLauncher == null) {
            this.serverLauncher = ServerBootstrapper.bootstrap();
            this.serverLauncher.launch();
        }
    }
}
