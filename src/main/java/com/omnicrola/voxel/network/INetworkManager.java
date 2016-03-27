package com.omnicrola.voxel.network;

/**
 * Created by Eric on 2/24/2016.
 */
public interface INetworkManager {
    void disconnect();

    void startLocalMultiplayerServer();

    void shutdownMultiplayer();

    void stopListeningForServers();

    void addObserver(INetworkObserver networkObserver);

    void startListeningForServers();

    void joinLobby(VoxelGameServer multiplayerServer);

    VoxelGameServer getCurrentServer();

    void removeObserver(INetworkObserver observer);
}
