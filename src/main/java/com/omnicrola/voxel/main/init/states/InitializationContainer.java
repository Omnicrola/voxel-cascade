package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.scene.Node;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.settings.IDisplayContext;
import com.omnicrola.voxel.settings.JmeDisplayContext;
import com.omnicrola.voxel.terrain.TerrainManager;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.ui.IUiManager;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;
import de.lessvoid.nifty.Nifty;

/**
 * Created by omnic on 2/28/2016.
 */
public class InitializationContainer {
    private WorldManager worldManager;
    private VoxelTypeLibrary voxelTypeLibrary;
    private MaterialRepository materialRepository;
    private VoxelGameEngine voxelGameEngine;
    private LevelManager levelManager;
    private WorldCommandProcessor worldCommandProcessor;
    private NetworkManager networkManager;
    private TerrainManager terrainManager;
    private WorldEntityBuilder worldEntityBuilder;
    private IUiManager uiManager;

    public InitializationContainer(WorldManager worldManager,
                                   VoxelTypeLibrary voxelTypeLibrary,
                                   MaterialRepository materialRepository,
                                   VoxelGameEngine voxelGameEngine,
                                   LevelManager levelManager,
                                   WorldCommandProcessor worldCommandProcessor,
                                   NetworkManager networkManager,
                                   TerrainManager terrainManager,
                                   WorldEntityBuilder worldEntityBuilder,
                                   IUiManager uiManager) {
        this.worldManager = worldManager;
        this.voxelTypeLibrary = voxelTypeLibrary;
        this.materialRepository = materialRepository;
        this.voxelGameEngine = voxelGameEngine;
        this.levelManager = levelManager;
        this.worldCommandProcessor = worldCommandProcessor;
        this.networkManager = networkManager;
        this.terrainManager = terrainManager;
        this.worldEntityBuilder = worldEntityBuilder;
        this.uiManager = uiManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public VoxelTypeLibrary getVoxelTypeLibrary() {
        return voxelTypeLibrary;
    }

    public MaterialRepository getMaterialRepository() {
        return materialRepository;
    }

    public AssetManager getAssetManager() {
        return voxelGameEngine.getAssetManager();
    }

    public ITickProvider getTicProvider() {
        return voxelGameEngine.getTicProvider();
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public Nifty getNiftyGui() {
        return this.voxelGameEngine.getNiftyGui();
    }

    public InputManager getInputManager() {
        return this.voxelGameEngine.getInputManager();
    }

    public AppStateManager getStateManager() {
        return this.voxelGameEngine.getStateManager();
    }

    public WorldCommandProcessor getWorldCommandProcessor() {
        return worldCommandProcessor;
    }

    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    public TerrainManager getTerrainManager() {
        return terrainManager;
    }

    public WorldEntityBuilder getWorldEntityBuilder() {
        return worldEntityBuilder;
    }

    public Node getGuiNode() {
        return this.voxelGameEngine.getGuiNode();
    }

    public IUiManager getUiManager() {
        return uiManager;
    }

    public IDisplayContext getAppSettings() {
        return new JmeDisplayContext(this.voxelGameEngine.getContext());
    }
}
