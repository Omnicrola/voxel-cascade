package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelLoadingAdapter;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class WorldManagerStateInitializer implements IStateInitializer {

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        WorldManager worldManager = initializationContainer.getWorldManager();
        ITickProvider ticProvider = initializationContainer.getTicProvider();

        AssetManager assetManager = initializationContainer.getAssetManager();
        LevelDefinitionRepository levelDefinitions = (LevelDefinitionRepository) assetManager.loadAsset(GameConstants.LEVEL_DEFINITIONS);

        LevelLoadingAdapter levelLoadingAdapter = new LevelLoadingAdapter();
        LevelManager levelManager = new LevelManager(levelDefinitions, levelLoadingAdapter);

        EntityControlAdapter entityControlAdapter = new EntityControlAdapter();
        UnitDefinitionRepository definitionRepository = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.DEFINITION_REPOSITORY_FILE);
        WorldEntityBuilder entityBuilder = new WorldEntityBuilder(definitionRepository, assetManager, levelManager, entityControlAdapter);


        return new WorldManagerState(ticProvider, levelManager, worldManager, entityBuilder);
    }
}
