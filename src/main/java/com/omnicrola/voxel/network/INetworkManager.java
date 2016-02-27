package com.omnicrola.voxel.network;

/**
 * Created by Eric on 2/24/2016.
 */
public interface INetworkManager {
    boolean connectTo(String serverAddress);

    void startMultiplayerServer();

    void shutdownMultiplayer();
}
