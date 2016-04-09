package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.audio.AudioRepository;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.load.*;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.states.LoadLevelState;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.world.build.StructureBuilder;

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
        return new LoadLevelState(asyncLevelLoader);
    }


    private ArrayList<ILoadingTaskFactory> createParallelTaskFactories(InitializationContainer initializationContainer) {

        AudioRepository audioRepository = initializationContainer.getAudioRepository();
        StructureBuilder structureBuilder = initializationContainer.getWorldEntityBuilder().getStructureBuilder();

        ArrayList<ILoadingTaskFactory> taskFactories = new ArrayList<>();
        taskFactories.add(new TerrainGeneratorTaskFactory());
        taskFactories.add(new CreateUnitsTaskFactory());
        taskFactories.add(new CreateStructuresTaskFactory(structureBuilder));
        taskFactories.add(new PreloadAudioTaskFactory(audioRepository));
        return taskFactories;
    }

    private ArrayList<ILoadingTaskFactory> createFinalTaskFactories() {
        ArrayList<ILoadingTaskFactory> taskFactories = new ArrayList<>();
        return taskFactories;
    }
}
