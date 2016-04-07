package com.omnicrola.voxel.network.events;

import com.omnicrola.voxel.network.VoxelGameServer;

import java.util.List;

/**
 * Created by Eric on 4/6/2016.
 */
public class MultiplayerServerListChangeEvent {
    private List<VoxelGameServer> multiplayerGames;

    public MultiplayerServerListChangeEvent(List<VoxelGameServer> multiplayerGames) {
        this.multiplayerGames = multiplayerGames;
    }

    public List<VoxelGameServer> getMultiplayerGames() {
        return multiplayerGames;
    }
}
