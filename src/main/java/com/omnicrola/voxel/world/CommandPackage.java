package com.omnicrola.voxel.world;

import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.commands.EntityCommandAdapter;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.IShutdown;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by Eric on 2/22/2016.
 */
public class CommandPackage {
    private IShutdown shutdown;
    private LevelManager levelManager;
    private INetworkManager networkManager;
    private WorldEntityBuilder entityBuilder;
    private WorldManager worldManager;
    private IUiManager uiManager;
    private ICommandProcessor commandProcessor;
    private ITerrainManager terrainManager;
    private EntityCommandAdapter entityCommandAdapter;
    private AppStateManager stateManager;

    public CommandPackage(
            IShutdown shutdown,
            LevelManager levelManager,
            INetworkManager networkManager,
            WorldEntityBuilder entityBuilder,
            IUiManager uiManager,
            WorldManager worldManager,
            ICommandProcessor commandProcessor,
            ITerrainManager terrainManager,
            EntityCommandAdapter entityCommandAdapter,
            AppStateManager stateManager) {
        this.shutdown = shutdown;
        this.levelManager = levelManager;
        this.networkManager = networkManager;
        this.entityBuilder = entityBuilder;
        this.uiManager = uiManager;
        this.worldManager = worldManager;
        this.commandProcessor = commandProcessor;
        this.terrainManager = terrainManager;
        this.entityCommandAdapter = entityCommandAdapter;
        this.stateManager = stateManager;
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

    public void shutdownAndExit() {
        this.networkManager.disconnect();
        this.networkManager.closeLocalMultiplayerServer();
        this.shutdown.shutdownAndExit();
    }

    public ICommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public ITerrainManager getTerrainManager() {
        return terrainManager;
    }

    public EntityCommandAdapter getEntityCommandAdapter() {
        return this.entityCommandAdapter;
    }

    public void enableState(Class<? extends AppState> stateClass) {
        this.stateManager.getState(stateClass).setEnabled(true);
    }
    public void disableState(Class<? extends AppState> stateClass) {
        this.stateManager.getState(stateClass).setEnabled(false);
    }
}
