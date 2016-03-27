package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.network.INetworkObserver;
import com.omnicrola.voxel.network.VoxelGameServer;

import java.util.List;

/**
 * Created by Eric on 3/20/2016.
 */
public class AvailableServerChangeObserver implements INetworkObserver {
    private MultiplayerBrowserController multiplayerController;

    public AvailableServerChangeObserver(MultiplayerBrowserController multiplayerController) {
        this.multiplayerController = multiplayerController;
    }

    @Override
    public void availableServersChanged(List<VoxelGameServer> servers) {
        this.multiplayerController.updateServerList(servers);
    }

    @Override
    public void multiplayerGameChanged(VoxelGameServer multiplayerGame) {

    }
}
