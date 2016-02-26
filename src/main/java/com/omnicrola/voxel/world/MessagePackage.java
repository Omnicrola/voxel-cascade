package com.omnicrola.voxel.world;

import com.omnicrola.voxel.engine.states.ILevelManager;
import com.omnicrola.voxel.engine.states.LevelManager;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.ui.IUiManager;

/**
 * Created by Eric on 2/22/2016.
 */
public class MessagePackage {
    private LevelManager levelManager;
    private IUiManager uiManager;
    private INetworkManager networkManager;
    private WorldEntityBuilder entityBuilder;
    private WorldManager worldManager;

    public MessagePackage(
            LevelManager levelManager,
            IUiManager uiManager,
            INetworkManager networkManager,
            WorldEntityBuilder entityBuilder,
            WorldManager worldManager) {
        this.levelManager = levelManager;
        this.uiManager = uiManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.worldManager = worldManager;
    }

    public ILevelManager getLevelManager() {
        return this.levelManager;
    }

    public IUiManager getUiManager() {
        return uiManager;
    }

    public INetworkManager getNetworkManager() {
        return networkManager;
    }

    public WorldEntityBuilder getEntityBuilder() {
        return this.entityBuilder;
    }

    public WorldManager getWorldManager() {
        return this.worldManager;
    }
}
