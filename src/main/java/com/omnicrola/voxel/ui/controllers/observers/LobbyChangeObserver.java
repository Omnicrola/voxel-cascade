package com.omnicrola.voxel.ui.controllers.observers;

import com.omnicrola.voxel.network.INetworkObserver;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.controllers.MultiplayerLobbyScreenController;

import java.util.List;

/**
 * Created by Eric on 3/27/2016.
 */
public class LobbyChangeObserver implements INetworkObserver {
    private MultiplayerLobbyScreenController lobbyController;

    public LobbyChangeObserver(MultiplayerLobbyScreenController lobbyController) {
        this.lobbyController = lobbyController;
    }

    @Override
    public void availableServersChanged(List<VoxelGameServer> servers) {

    }

    @Override
    public void multiplayerGameChanged(VoxelGameServer multiplayerGame) {
        lobbyController.setCurrentGame(multiplayerGame);
    }
}
