package com.omnicrola.voxel.jme.wrappers;

import java.util.UUID;

/**
 * Created by Eric on 2/21/2016.
 */
public interface IGameNetwork {
    void connectTo(String servername);

    void loadLevel(UUID levelId);

    void closeConnection();

    void startLocalServer();
}
