package com.omnicrola.voxel.jme.wrappers;

/**
 * Created by Eric on 2/21/2016.
 */
public interface IGameNetwork {
    void connectTo(String servername);

    void loadLevel(String levelName);

    void closeConnection();

    void startLocalServer();
}
