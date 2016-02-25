package com.omnicrola.voxel.world;

import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.engine.states.IWorldLevelManager;
import com.omnicrola.voxel.engine.states.VoxelTerrainState;
import com.omnicrola.voxel.engine.states.WorldLevelState;
import com.omnicrola.voxel.network.INetworkManager;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.IUiManager;

/**
 * Created by Eric on 2/22/2016.
 */
public class MessagePackage {
    private WorldLevelState worldLevelState;
    private final VoxelTerrainState voxelTerrainState;
    private LevelDefinitionRepository levelDefinitionRepository;
    private IUiManager uiManager;
    private INetworkManager networkManager;

    public MessagePackage(
            WorldLevelState worldLevelState,
            VoxelTerrainState voxelTerrainState,
            LevelDefinitionRepository levelDefinitionRepository,
            IUiManager uiManager,
            INetworkManager networkManager) {
        this.worldLevelState = worldLevelState;
        this.voxelTerrainState = voxelTerrainState;
        this.levelDefinitionRepository = levelDefinitionRepository;
        this.uiManager = uiManager;
        this.networkManager = networkManager;
    }

    public IWorldLevelManager getWorldLevelManager() {
        return this.worldLevelState;
    }

    public ITerrainManager getVoxelTerrainManager() {
        return this.voxelTerrainState;
    }

    public LevelDefinitionRepository getLevelDefinitionRepository() {
        return this.levelDefinitionRepository;
    }

    public IUiManager getUiManager() {
        return uiManager;
    }

    public INetworkManager getNetworkManager() {
        return networkManager;
    }
}
