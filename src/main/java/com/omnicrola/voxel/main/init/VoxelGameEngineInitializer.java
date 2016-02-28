package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.commands.INetworkCommandQueue;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelLoadingAdapter;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.main.init.states.IStateInitializer;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.NetworkCommandQueue;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.data.VoxelType;
import com.omnicrola.voxel.world.CommandPackage;
import com.omnicrola.voxel.world.WorldManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {

    private InputMappingLoader inputMappingLoader;
    private GuiInitializer guiInitializer;
    private List<IStateInitializer> stateInitializers;

    public VoxelGameEngineInitializer(InputMappingLoader inputMappingLoader, GuiInitializer guiInitializer, List<IStateInitializer> stateInitializers) {
        this.inputMappingLoader = inputMappingLoader;
        this.guiInitializer = guiInitializer;
        this.stateInitializers = stateInitializers;
    }

    public void initialize(VoxelGameEngine voxelGameEngine) {
        this.inputMappingLoader.createInputMappings(voxelGameEngine.getInputManager());

        InitializationContainer initializationContainer = buildInitializationContainer(voxelGameEngine);

        this.guiInitializer.createGui(initializationContainer);

        AppStateManager stateManager = voxelGameEngine.getStateManager();
        this.stateInitializers.forEach(i -> stateManager.attach(i.buildState(initializationContainer)));
    }

    private InitializationContainer buildInitializationContainer(VoxelGameEngine voxelGameEngine) {
        WorldManager worldManager = new WorldManager(voxelGameEngine.getWorldNode());
        AssetManager assetManager = voxelGameEngine.getAssetManager();

        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();
        MaterialRepository materialRepository = new MaterialRepository(voxelGameEngine.getAssetManager());

        LevelDefinitionRepository levelDefinitions = (LevelDefinitionRepository) assetManager.loadAsset(GameConstants.LEVEL_DEFINITIONS);
        LevelLoadingAdapter levelLoadingAdapter = new LevelLoadingAdapter();
        LevelManager levelManager = new LevelManager(levelDefinitions, levelLoadingAdapter);

        CommandPackage commandPackage = new CommandPackage();
        INetworkCommandQueue networkCommandQueue  = new NetworkCommandQueue();
        WorldCommandProcessor worldCommandProcessor = new WorldCommandProcessor(commandPackage, networkCommandQueue);

        InitializationContainer initializationContainer = new InitializationContainer(
                worldManager,
                voxelTypeLibrary,
                materialRepository,
                voxelGameEngine,
                levelManager,
                worldCommandProcessor);
        return initializationContainer;
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }
}
