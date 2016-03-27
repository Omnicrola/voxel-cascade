package com.omnicrola.voxel.network;

import com.omnicrola.voxel.engine.IActionQueue;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerDiscovery {
    private static final Logger LOGGER = Logger.getLogger(MultiplayerDiscovery.class.getName());
    private List<InetAddress> lastServerList;

    private IActionQueue actionQueue;
    private MultiplayerDiscoveryManager multiplayerDiscoveryManager;

    public MultiplayerDiscovery(IActionQueue actionQueue) {
        this.actionQueue = actionQueue;
        this.lastServerList = new ArrayList<>();
    }

    public void startSearching() {
        if (this.multiplayerDiscoveryManager == null) {
            this.multiplayerDiscoveryManager = new MultiplayerDiscoveryManager();
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
        List<InetAddress> activeServers = this.multiplayerDiscoveryManager.getActiveServers();
        if (serversChanged(activeServers)) {
            return true;
        }
        return false;
    }

    private boolean serversChanged(List<InetAddress> activeServers) {
        if (activeServers.size() != this.lastServerList.size()) {
            return true;
        }
        for (InetAddress address : activeServers) {
            if (!this.lastServerList.contains(address)) {
                return false;
            }
        }
        return true;
    }

    public List<VoxelGameServer> getServers() {
        this.lastServerList = this.multiplayerDiscoveryManager.getActiveServers();
        return this.lastServerList.stream()
                .map(s -> new VoxelGameServer(s))
                .collect(Collectors.toList());
    }

    public void cleanup() {
        if (this.multiplayerDiscoveryManager != null) {
            this.multiplayerDiscoveryManager.setIsRunning(false);
        }
    }
}
