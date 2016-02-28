package com.omnicrola.voxel.main.init;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseAxisTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.level.LevelDefinitionRepository;
import com.omnicrola.voxel.data.level.LevelStateLoader;
import com.omnicrola.voxel.data.units.UnitDefinitionRepository;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.*;
import com.omnicrola.voxel.engine.states.transitions.TransitionActivePlay;
import com.omnicrola.voxel.engine.states.transitions.TransitionMainMenu;
import com.omnicrola.voxel.engine.states.transitions.TransitionMultiplayerLoad;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.main.init.states.IStateInitializer;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.network.ClientNetworkState;
import com.omnicrola.voxel.settings.GameConstants;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.data.VoxelType;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorProviderBuilder;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.builders.ActivePlayUiBuilder;
import com.omnicrola.voxel.ui.builders.GameOverUiBuilder;
import com.omnicrola.voxel.ui.builders.MainMenuUiBuilder;
import com.omnicrola.voxel.ui.builders.MultiplayerUiBuilder;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;
import de.lessvoid.nifty.Nifty;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by omnic on 1/15/2016.
 */
public class VoxelGameEngineInitializer {

    private List<IStateInitializer> stateInitializers;

    public VoxelGameEngineInitializer(List<IStateInitializer> stateInitializers) {
        this.stateInitializers = stateInitializers;
    }

    public void initialize(VoxelGameEngine voxelGameEngine) {
        InitializationContainer initializationContainer = buildInitializationContainer(voxelGameEngine);
        AppStateManager stateManager = voxelGameEngine.getStateManager();

        this.stateInitializers.forEach(i -> stateManager.attach(i.buildState(initializationContainer)));
    }

    private InitializationContainer buildInitializationContainer(VoxelGameEngine voxelGameEngine) {
        WorldManager worldManager = new WorldManager(voxelGameEngine.getWorldNode());

        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();
        MaterialRepository materialRepository = new MaterialRepository(voxelGameEngine.getAssetManager());
        PhysicsSpace physicsSpace = voxelGameEngine.getPhysicsSpace();

        InitializationContainer initializationContainer = new InitializationContainer(
                worldManager,
                voxelTypeLibrary,
                materialRepository,
                physicsSpace);
        return initializationContainer;
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }

    public void initializeGame(VoxelGameEngine voxelGameEngine) {
        createStates(voxelGameEngine);
        createInputMappings(voxelGameEngine.getInputManager());
    }

    private void createStates(VoxelGameEngine voxelGameEngine) {

        LevelDefinitionRepository levelDefinitionRepository = this.gameDataParser.loadLevels(GameConstants.LEVEL_DEFINITIONS);
        UnitDefinitionRepository definitionRepository = (UnitDefinitionRepository) assetManager.loadAsset(GameConstants.DEFINITION_REPOSITORY_FILE);


        CursorProviderBuilder cursorProviderBuilder = new CursorProviderBuilder();
        Cursor2dProvider cursor2dProvider = cursorProviderBuilder.build(assetManager);
        WorldManager worldManager = new WorldManager(voxelGameEngine.getWorldNode());
        WorldManagerState worldManagerState = new WorldManagerState(gameDataParser, cursor2dProvider, worldManager);
        ClientNetworkState clientNetworkState = new ClientNetworkState();

        WorldEntityBuilder worldEntityBuilder = new WorldEntityBuilder(definitionRepository, assetManager, levelManager, new EntityControlAdapter());
        LevelStateLoader levelStateLoader = new LevelStateLoader(
                voxelGameEngine,
                clientNetworkState,
                voxelTerrainState,
                worldManager,
                worldEntityBuilder,
                cursor2dProvider);
        LevelManager levelManager = new LevelManager(levelDefinitionRepository, levelStateLoader);
        ActivePlayInputState playState = new ActivePlayInputState(levelManager);
        MainMenuState mainMenuState = new MainMenuState();
        GameOverState gameOverState = new GameOverState();
        ShadowState shadowState = new ShadowState();

        AppStateManager stateManager = voxelGameEngine.getStateManager();
        stateManager.attach(debugState);
        stateManager.attach(loadingState);

        stateManager.attach(voxelTerrainState);
        stateManager.attach(worldManagerState);
        stateManager.attach(clientNetworkState);

        stateManager.attach(mainMenuState);
        stateManager.attach(playState);
        stateManager.attach(gameOverState);
        stateManager.attach(shadowState);

        createGui(voxelGameEngine, levelManager, worldManagerState, stateManager);
    }

    private void createGui(VoxelGameEngine voxelGameEngine,
                           LevelManager levelManager,
                           WorldManagerState worldManagerState,
                           AppStateManager stateManager) {

        Nifty niftyGui = voxelGameEngine.getNiftyGui();

        Map<GlobalGameState, IStateTransition> transitions = new HashMap<>();
        transitions.put(GlobalGameState.MULTIPLAYER_LOAD, new TransitionMultiplayerLoad());
        transitions.put(GlobalGameState.ACTIVE_PLAY, new TransitionActivePlay());
        transitions.put(GlobalGameState.MAIN_MENU, new TransitionMainMenu());
        UiAdapter uiAdapter = new UiAdapter(niftyGui, levelManager, worldManagerState, transitions, stateManager);
        ActivePlayUiBuilder.build(uiAdapter);

        GameOverUiBuilder.build(uiAdapter);
        MainMenuUiBuilder.build(uiAdapter);
        MultiplayerUiBuilder.build(uiAdapter);
    }

    private void createInputMappings(InputManager inputManager) {
        inputManager.deleteMapping(SimpleApplication.INPUT_MAPPING_EXIT);

        addMouseMapping(inputManager, GameInputAction.MOUSE_SECONDARY, MouseInput.BUTTON_RIGHT);
        addMouseMapping(inputManager, GameInputAction.MOUSE_PRIMARY, MouseInput.BUTTON_LEFT);
        inputManager.addMapping(GameInputAction.MOUSE_MOVEMENT.toString(),
                new MouseAxisTrigger(MouseInput.AXIS_X, true),
                new MouseAxisTrigger(MouseInput.AXIS_X, false),
                new MouseAxisTrigger(MouseInput.AXIS_Y, true),
                new MouseAxisTrigger(MouseInput.AXIS_Y, false));

        addKeyMapping(inputManager, GameInputAction.SELECT, KeyInput.KEY_RETURN);
        addKeyMapping(inputManager, GameInputAction.CLEAR_SELECTION, KeyInput.KEY_ESCAPE);
        addKeyMapping(inputManager, GameInputAction.MULTI_SELECT, KeyInput.KEY_LSHIFT, KeyInput.KEY_RSHIFT);

        addKeyMapping(inputManager, GameInputAction.CAMERA_FORWARD, KeyInput.KEY_UP);
        addKeyMapping(inputManager, GameInputAction.CAMERA_BACKWARD, KeyInput.KEY_DOWN);
        addKeyMapping(inputManager, GameInputAction.CAMERA_LEFT, KeyInput.KEY_LEFT);
        addKeyMapping(inputManager, GameInputAction.CAMERA_RIGHT, KeyInput.KEY_RIGHT);

        addKeyMapping(inputManager, GameInputAction.TOGGLE_DEBUG_MODE, KeyInput.KEY_F3);
        addKeyMapping(inputManager, GameInputAction.DEBUG_RELOAD_LEVEL, KeyInput.KEY_R);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_MOUSE_LOOK, KeyInput.KEY_TAB);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_PHYSICS, KeyInput.KEY_1);
        addKeyMapping(inputManager, GameInputAction.DEBUG_REBUILD_TERRAIN, KeyInput.KEY_2);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TOGGLE_WIREFRAME, KeyInput.KEY_4);
        addKeyMapping(inputManager, GameInputAction.DEBUG_TARGET_OBJECT, KeyInput.KEY_T);
        addKeyMapping(inputManager, GameInputAction.DEBUG_SCENE_GRAPH, KeyInput.KEY_G);
    }

    private void addMouseMapping(InputManager inputManager, GameInputAction action, int buttonCode) {
        inputManager.addMapping(action.trigger(), new MouseButtonTrigger(buttonCode));
    }

    private void addKeyMapping(InputManager inputManager, GameInputAction action, int... keys) {
        for (int i = 0; i < keys.length; i++) {
            inputManager.addMapping(action.trigger(), new KeyTrigger(keys[i]));
        }
    }
}
