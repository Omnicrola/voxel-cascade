package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelLoadingAdapter;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.ShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.main.init.states.IStateInitializer;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.ClientListenerBuilder;
import com.omnicrola.voxel.network.NetworkCommandQueue;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.*;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.build.TerrainQuadFactory;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.VoxelType;
import com.omnicrola.voxel.ui.UiManager;
import com.omnicrola.voxel.world.WorldEntityBuilder;
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

        LevelDefinitionRepository levelDefinitions = (LevelDefinitionRepository) assetManager.loadAsset(GameConstants.LEVEL_DEFINITIONS_DIRECTORY);
        UnitDefinitionRepository unitDefintions = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.UNIT_DEFINITION_FILE);

        LevelLoadingAdapter levelLoadingAdapter = new LevelLoadingAdapter();
        LevelManager levelManager = new LevelManager(levelDefinitions, levelLoadingAdapter);

        NetworkCommandQueue networkCommandQueue = new NetworkCommandQueue();

        ShutdownHandler shutdownHandler = new ShutdownHandler(voxelGameEngine);

        ClientListenerBuilder clientListenerBuilder = new ClientListenerBuilder(voxelGameEngine);
        NetworkManager networkManager = new NetworkManager(clientListenerBuilder, networkCommandQueue);

        TerrainAdapter terrainAdapter = new TerrainAdapter(worldManager, materialRepository, voxelTypeLibrary, voxelGameEngine.getPhysicsSpace());
        VoxelChunkHandler voxelChunkHandler = buildVoxelChunkHandler(worldManager, materialRepository, terrainAdapter);
        VoxelTerrainGenerator voxelTerrainGenerator = buildTerrainGenerator(voxelTypeLibrary);

        TerrainManager terrainManager = new TerrainManager(voxelChunkHandler, voxelTerrainGenerator);

        EntityControlAdapter entityControlAdapter = new EntityControlAdapter();
        WorldEntityBuilder worldEntityBuilder = new WorldEntityBuilder(unitDefintions, assetManager, levelManager, entityControlAdapter);
        UiManager uiManager = new UiManager(voxelGameEngine.getNiftyGui());

        WorldCommandProcessor worldCommandProcessor = new WorldCommandProcessor(
                networkCommandQueue,
                shutdownHandler,
                levelManager,
                networkManager,
                worldEntityBuilder,
                uiManager,
                worldManager,
                terrainManager);
        networkManager.setCommandProcessor(worldCommandProcessor);

        InitializationContainer initializationContainer = new InitializationContainer(
                worldManager,
                voxelTypeLibrary,
                materialRepository,
                voxelGameEngine,
                levelManager,
                worldCommandProcessor,
                networkManager, terrainManager);
        return initializationContainer;
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }


    private VoxelTerrainGenerator buildTerrainGenerator(VoxelTypeLibrary voxelTypeLibrary) {
        PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator();
        return new VoxelTerrainGenerator(perlinNoiseGenerator, voxelTypeLibrary);
    }

    private VoxelChunkHandler buildVoxelChunkHandler(WorldManager worldManager, MaterialRepository materialRepository, TerrainAdapter terrainAdapter) {
        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, worldManager);

        return new VoxelChunkHandler(terrainAdapter, voxelChunkRebuilder);
    }
}
