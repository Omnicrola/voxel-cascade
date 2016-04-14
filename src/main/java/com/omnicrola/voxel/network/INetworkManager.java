package com.omnicrola.voxel.network;

/**
 * Created by Eric on 2/24/2016.
 */
public interface INetworkManager {
    void disconnect();

    void startLocalMultiplayerServer();

    void stopLocalMultiplayerServer();

    void stopListeningForServers();

    void startListeningForServers();

    boolean joinLobby(VoxelGameServer multiplayerServer);

    VoxelGameServer getCurrentServer();

    void setPlayerServerId(int playerId);
}
