package com.omnicrola.voxel.network;

import java.net.InetAddress;

/**
 * Created by Eric on 2/24/2016.
 */
public interface INetworkManager {
    void disconnect();

    boolean connectTo(String serverAddress);

    void startMultiplayerServer();

    void shutdownMultiplayer();

    void stopListeningForServers();

    void addObserver(INetworkObserver networkObserver);

    void startListeningForServers();

}
