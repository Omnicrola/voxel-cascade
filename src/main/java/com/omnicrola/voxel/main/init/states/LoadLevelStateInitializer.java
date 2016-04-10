package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.load.*;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.states.LoadLevelState;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.build.StructureBuilder;
import com.omnicrola.voxel.world.build.UnitBuilder;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

import java.util.ArrayList;

/**
 * Created by Eric on 4/8/2016.
 */
public class LoadLevelStateInitializer implements IStateInitializer {
    GameXmlDataParser gameXmlDataParser;

    public LoadLevelStateInitializer(GameXmlDataParser gameXmlDataParser) {
        this.gameXmlDataParser = gameXmlDataParser;
    }

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {

        LevelDefinitionRepository levelDefinitions = this.gameXmlDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS_DIRECTORY);
        AssetManager assetManager = initializationContainer.getAssetManager();
        UnitDefinitionRepository unitDefinitions = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.UNIT_DEFINITION_FILE);

        ArrayList<ILoadingTaskFactory> parallelTaskFactories = createParallelTaskFactories(initializationContainer);
        ArrayList<ILoadingTaskFactory> finalTaskFactories = createFinalTaskFactories();
        AsyncLevelLoader asyncLevelLoader = new AsyncLevelLoader(parallelTaskFactories, finalTaskFactories, levelDefinitions, unitDefinitions);

        LevelManager levelManager = initializationContainer.getLevelManager();
        return new LoadLevelState(levelManager, asyncLevelLoader);
    }

    private ArrayList<ILoadingTaskFactory> createParallelTaskFactories(InitializationContainer initializationContainer) {

        AudioRepository audioRepository = initializationContainer.getAudioRepository();
        WorldEntityBuilder worldEntityBuilder = initializationContainer.getWorldEntityBuilder();
        StructureBuilder structureBuilder = worldEntityBuilder.getStructureBuilder();
        AssetManager assetManager = initializationContainer.getAssetManager();
        UnitBuilder unitBuilder = worldEntityBuilder.getUnitBuilder();

        ArrayList<ILoadingTaskFactory> taskFactories = new ArrayList<>();
        taskFactories.add(new TerrainGeneratorTaskFactory(assetManager));
        taskFactories.add(new CreateUnitsTaskFactory(unitBuilder));
        taskFactories.add(new CreateStructuresTaskFactory(structureBuilder));
        taskFactories.add(new PreloadAudioTaskFactory(audioRepository));
        return taskFactories;
    }

    private ArrayList<ILoadingTaskFactory> createFinalTaskFactories() {
        ArrayList<ILoadingTaskFactory> taskFactories = new ArrayList<>();
        taskFactories.add(new AdjustUnitPlacementTaskFactory());
        return taskFactories;
    }
}
