package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.network.INetworkObserver;

/**
 * Created by Eric on 3/20/2016.
 */
public class AvailableServerChangeObserver implements INetworkObserver {
    private MultiplayerBrowserController multiplayerController;

    public AvailableServerChangeObserver(MultiplayerBrowserController multiplayerController) {
        this.multiplayerController = multiplayerController;
    }
}
