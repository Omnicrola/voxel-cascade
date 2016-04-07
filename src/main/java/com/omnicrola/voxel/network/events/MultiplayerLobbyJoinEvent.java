package com.omnicrola.voxel.network.events;

import com.omnicrola.voxel.network.VoxelGameServer;

/**
 * Created by Eric on 4/6/2016.
 */
public class MultiplayerLobbyJoinEvent {
    private VoxelGameServer multiplayerServer;

    public MultiplayerLobbyJoinEvent(VoxelGameServer multiplayerServer) {
        this.multiplayerServer = multiplayerServer;
    }

    public VoxelGameServer getMultiplayerServer() {
        return multiplayerServer;
    }
}
