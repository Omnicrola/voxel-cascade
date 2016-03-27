package com.omnicrola.voxel.network;

import com.omnicrola.voxel.engine.IActionQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerDiscovery {
    private static final Logger LOGGER = Logger.getLogger(MultiplayerDiscovery.class.getName());
    private List<VoxelGameServer> lastServerList;

    private IActionQueue actionQueue;
    private MultiplayerDiscoveryManager multiplayerDiscoveryManager;

    public MultiplayerDiscovery(IActionQueue actionQueue) {
        this.actionQueue = actionQueue;
        this.lastServerList = new ArrayList<>();
    }

    public void startSearching() {
        if (this.multiplayerDiscoveryManager == null) {
            this.multiplayerDiscoveryManager = new MultiplayerDiscoveryManager(new BroadcastPacketParser());
            this.multiplayerDiscoveryManager.start();
        } else {
            LOGGER.log(Level.WARNING, "Broadcast receiver already started.");
        }
    }

    public void stopSearching() {
        if (this.multiplayerDiscoveryManager != null) {
            this.multiplayerDiscoveryManager.setIsRunning(false);
            this.multiplayerDiscoveryManager = null;
        }
    }

    public boolean hasNewServers() {
        if (this.multiplayerDiscoveryManager == null) {
            return false;
        }
        List<VoxelGameServer> activeServers = this.multiplayerDiscoveryManager.getActiveServers();
        if (serversChanged(activeServers)) {
            return true;
        }
        return false;
    }

    private boolean serversChanged(List<VoxelGameServer> activeServers) {
        if (activeServers.size() != this.lastServerList.size()) {
            return true;
        }
        for (VoxelGameServer address : activeServers) {
            if (!this.lastServerList.contains(address)) {
                return false;
            }
        }
        return true;
    }

    public List<VoxelGameServer> getServers() {
        this.lastServerList = this.multiplayerDiscoveryManager.getActiveServers();
        return this.lastServerList;
    }

    public void cleanup() {
        if (this.multiplayerDiscoveryManager != null) {
            this.multiplayerDiscoveryManager.setIsRunning(false);
        }
    }
}
