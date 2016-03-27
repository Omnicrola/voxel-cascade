package com.omnicrola.voxel.main.init;

import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.InputManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelLoadingAdapter;
import com.omnicrola.voxel.data.level.LevelStateLoader;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.ShutdownHandler;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.build.ProjectileBuilder;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.input.SelectionFrustrumFactory;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.main.init.states.IStateInitializer;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.ClientListenerBuilder;
import com.omnicrola.voxel.network.NetworkCommandQueue;
import com.omnicrola.voxel.network.NetworkManager;
import com.omnicrola.voxel.network.MultiplayerDiscovery;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.*;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.build.mesh.*;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.terrain.data.VoxelType;
import com.omnicrola.voxel.ui.UiManager;
import com.omnicrola.voxel.ui.select.RingMesh;
import com.omnicrola.voxel.ui.select.UiSelectionRectangle;
import com.omnicrola.voxel.world.IWorldNode;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.StructureBuilder;
import com.omnicrola.voxel.world.build.UnitBuilder;
import com.omnicrola.voxel.world.build.WorldBuilderToolbox;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {

    private InputMappingLoader inputMappingLoader;
    private GuiInitializer guiInitializer;
    private List<IStateInitializer> stateInitializers;
    private GameXmlDataParser gameXmlDataParser;

    public VoxelGameEngineInitializer(InputMappingLoader inputMappingLoader,
                                      GuiInitializer guiInitializer,
                                      List<IStateInitializer> stateInitializers,
                                      GameXmlDataParser gameXmlDataParser) {
        this.inputMappingLoader = inputMappingLoader;
        this.guiInitializer = guiInitializer;
        this.stateInitializers = stateInitializers;
        this.gameXmlDataParser = gameXmlDataParser;
    }

    public void initialize(VoxelGameEngine voxelGameEngine) {
        this.inputMappingLoader.createInputMappings(voxelGameEngine.getInputManager());

        InitializationContainer initializationContainer = buildInitializationContainer(voxelGameEngine);

        this.guiInitializer.createGui(initializationContainer);

        AppStateManager stateManager = voxelGameEngine.getStateManager();
        this.stateInitializers.forEach(i -> stateManager.attach(i.buildState(initializationContainer)));
    }

    private InitializationContainer buildInitializationContainer(VoxelGameEngine voxelGameEngine) {
        WorldCursor worldCursor = createWorldCursor(voxelGameEngine);

        WorldManager worldManager = new WorldManager(voxelGameEngine.getWorldNode(), worldCursor);
        AssetManager assetManager = voxelGameEngine.getAssetManager();

        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();
        MaterialRepository materialRepository = new MaterialRepository(voxelGameEngine.getAssetManager());

        LevelDefinitionRepository levelDefinitions = this.gameXmlDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS_DIRECTORY);
        UnitDefinitionRepository unitDefinitions = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.UNIT_DEFINITION_FILE);

        LevelLoadingAdapter levelLoadingAdapter = new LevelLoadingAdapter();
        LevelManager levelManager = new LevelManager(levelDefinitions, levelLoadingAdapter);
        NetworkCommandQueue networkCommandQueue = new NetworkCommandQueue();
        ShutdownHandler shutdownHandler = new ShutdownHandler(voxelGameEngine);

        ClientListenerBuilder clientListenerBuilder = new ClientListenerBuilder(voxelGameEngine);
        MultiplayerDiscovery multiplayerDiscovery = new MultiplayerDiscovery(voxelGameEngine);
        NetworkManager networkManager = new NetworkManager(clientListenerBuilder, networkCommandQueue, multiplayerDiscovery);

        TerrainManager terrainManager = createTerrainManager(voxelGameEngine, worldManager, voxelTypeLibrary, materialRepository);

        InputManager inputManager = voxelGameEngine.getInputManager();
        WorldEntityBuilder worldEntityBuilder = createWorldEntityBuilder(assetManager, unitDefinitions, levelManager, terrainManager, worldManager, materialRepository, inputManager);
        UiSelectionRectangle selectionRectangle = buildSelectionRectangle(assetManager);
        UiManager uiManager = new UiManager(voxelGameEngine.getNiftyGui(), inputManager, selectionRectangle);

        WorldCommandProcessor worldCommandProcessor = new WorldCommandProcessor(
                networkCommandQueue,
                shutdownHandler,
                levelManager,
                networkManager,
                worldEntityBuilder,
                uiManager,
                worldManager,
                terrainManager,
                voxelGameEngine.getStateManager());
        networkManager.setCommandProcessor(worldCommandProcessor);

        LevelStateLoader levelStateLoader = new LevelStateLoader(
                voxelGameEngine,
                terrainManager,
                worldManager,
                worldCommandProcessor,
                levelManager);
        levelLoadingAdapter.setStateLoader(levelStateLoader);

        InitializationContainer initializationContainer = new InitializationContainer(
                worldManager,
                voxelTypeLibrary,
                materialRepository,
                voxelGameEngine,
                levelManager,
                worldCommandProcessor,
                networkManager,
                terrainManager, worldEntityBuilder, uiManager);
        return initializationContainer;
    }

    private UiSelectionRectangle buildSelectionRectangle(AssetManager assetManager) {
        Material material = getSelectionMaterial(assetManager);
        RingMesh ringMesh = new RingMesh();
        Geometry geometry = new Geometry("selection quad", ringMesh);
        geometry.setMaterial(material);

        UiSelectionRectangle uiSelectionRectangle = new UiSelectionRectangle(ringMesh);
        uiSelectionRectangle.attachChild(geometry);
        return uiSelectionRectangle;
    }

    private Material getSelectionMaterial(AssetManager assetManager) {
        Material material = new Material(assetManager, GameConstants.MATERIAL_GUI);
        material.setColor("Color", new ColorRGBA(0.1f, 1.0f, 0.1f, 1.0f));
        return material;
    }

    private WorldCursor createWorldCursor(VoxelGameEngine voxelGameEngine) {
        Camera camera = voxelGameEngine.getCamera();
        IWorldNode worldNode = voxelGameEngine.getWorldNode();
        JmeInputWrapper inputManager = new JmeInputWrapper(voxelGameEngine.getInputManager(), voxelGameEngine.getFlyByCamera());
        WorldCursor worldCursor = new WorldCursor(inputManager, camera, new SelectionFrustrumFactory(camera), worldNode);
        return worldCursor;
    }

    private TerrainManager createTerrainManager(VoxelGameEngine voxelGameEngine,
                                                WorldManager worldManager,
                                                VoxelTypeLibrary voxelTypeLibrary,
                                                MaterialRepository materialRepository) {

        TerrainAdapter terrainAdapter = new TerrainAdapter(worldManager, materialRepository, voxelTypeLibrary);
        VoxelChunkHandler voxelChunkHandler = buildVoxelChunkHandler(worldManager, materialRepository, terrainAdapter);
        VoxelTerrainGenerator voxelTerrainGenerator = buildTerrainGenerator(voxelTypeLibrary);
        TerrainManager terrainManager = new TerrainManager(voxelChunkHandler, voxelTerrainGenerator, voxelTypeLibrary);
        return terrainManager;
    }

    private WorldEntityBuilder createWorldEntityBuilder(AssetManager assetManager,
                                                        UnitDefinitionRepository unitDefintions,
                                                        LevelManager levelManager,
                                                        ITerrainManager terrainManager,
                                                        WorldManager worldManager,
                                                        MaterialRepository materialRepository, InputManager inputManager) {
        WorldBuilderToolbox toolbox = new WorldBuilderToolbox(assetManager, levelManager, unitDefintions);
        ProjectileBuilder projectileBuilder = new ProjectileBuilder(toolbox, materialRepository);
        ParticleBuilder particleBuilder = new ParticleBuilder(assetManager);
        EntityControlAdapter entityControlAdapter = new EntityControlAdapter(
                particleBuilder,
                worldManager,
                projectileBuilder,
                terrainManager,
                levelManager,
                inputManager);
        UnitBuilder unitBuilder = new UnitBuilder(toolbox, entityControlAdapter, materialRepository);
        StructureBuilder structureBuilder = new StructureBuilder(toolbox, entityControlAdapter, materialRepository);
        return new WorldEntityBuilder(unitDefintions, assetManager, unitBuilder, structureBuilder);
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
        ITerrainQuadMeshStrategy[] meshStrategies = new ITerrainQuadMeshStrategy[6];
        meshStrategies[VoxelChunk.SIDE_Z_NEG] = new QuadMeshStrategyZAxis();
        meshStrategies[VoxelChunk.SIDE_Z_POS] = new QuadMeshStrategyZAxis();
        meshStrategies[VoxelChunk.SIDE_X_POS] = new QuadMeshStrategyXAxis();
        meshStrategies[VoxelChunk.SIDE_X_NEG] = new QuadMeshStrategyXAxis();
        meshStrategies[VoxelChunk.SIDE_Y_POS] = new QuadMeshStrategyYPos();
        meshStrategies[VoxelChunk.SIDE_Y_NEG] = new StandardQuadMeshStrategy();

        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository, meshStrategies);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, worldManager);

        return new VoxelChunkHandler(terrainAdapter, voxelChunkRebuilder);
    }
}
