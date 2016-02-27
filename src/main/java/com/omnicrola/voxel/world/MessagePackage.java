package com.omnicrola.voxel.world;

import com.omnicrola.voxel.commands.IMessageProcessor;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.IShutdown;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.ui.IUiManager;

/**
 * Created by Eric on 2/22/2016.
 */
public class MessagePackage {
    private IShutdown shutdown;
    private LevelManager levelManager;
    private ClientNetworkState networkManager;
    private WorldEntityBuilder entityBuilder;
    private WorldManager worldManager;
    private IUiManager uiManager;

    public MessagePackage(
            IShutdown shutdown,
            LevelManager levelManager,
            ClientNetworkState networkManager,
            WorldEntityBuilder entityBuilder,
            IUiManager uiManager,
            WorldManager worldManager) {
        this.shutdown = shutdown;
        this.levelManager = levelManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.uiManager = uiManager;
        this.worldManager = worldManager;
    }

    public ILevelManager getLevelManager() {
        return this.levelManager;
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

    public IUiManager getUiManager() {
        return this.uiManager;
    }

    public IMessageProcessor getMessageProcessor() {
        return this.networkManager;
    }

    public void shutdownAndExit() {
        this.networkManager.disconnect();
        this.networkManager.shutdownMultiplayer();
        this.shutdown.shutdownAndExit();
    }
}
