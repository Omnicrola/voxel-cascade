package com.omnicrola.voxel.data.level;

import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.omnicrola.voxel.commands.WorldCommandProcessor;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.main.init.states.InitializationContainer;
import com.omnicrola.voxel.terrain.TerrainManager;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.ui.CursorProviderBuilder;
import com.omnicrola.voxel.world.WorldManager;
import com.omnicrola.voxel.world.build.WorldEntityBuilder;

/**
 * Created by omnic on 4/9/2016.
 */
public class LevelPlayInitializer {

    private InitializationContainer initializationContainer;
    private CursorProviderBuilder cursorProviderBuilder;

    public LevelPlayInitializer(InitializationContainer initializationContainer,
                                CursorProviderBuilder cursorProviderBuilder) {
        this.initializationContainer = initializationContainer;
        this.cursorProviderBuilder = cursorProviderBuilder;
    }

    public void activate(LevelData levelData, IWorldCursor worldCursor, Camera camera) {
        TerrainManager terrainManager = initializationContainer.getTerrainManager();
        WorldManager worldManager = initializationContainer.getWorldManager();
        LevelManager levelManager = initializationContainer.getLevelManager();


        terrainManager.setCurrentHandler(levelData.terrain);
        worldManager.addTerrain(levelData.terrain);

        levelData.structures.forEach(s -> worldManager.addStructure(s));
        levelData.units.forEach(u -> worldManager.addUnit(u));

        LevelDefinition levelDefinition = levelData.levelDefinition;
        LevelState levelState = new LevelState(levelDefinition.getName());
        levelManager.setCurrentLevel(levelState);

        TeamId playerTeam = levelManager.getCurrentLevel().getPlayerTeam();
        buildCursorInteractionStrategies(worldCursor, playerTeam);

        camera.setRotation(levelDefinition.getCameraOrientation());
        camera.setLocation(levelDefinition.getCameraPosition());
    }

    private void buildCursorInteractionStrategies(IWorldCursor worldCursor, TeamId playerTeam) {
        Cursor2dProvider cursor2dProvider = cursorProviderBuilder.build(initializationContainer.getAssetManager());
        InputManager inputManager = initializationContainer.getInputManager();
        WorldEntityBuilder worldEntityBuilder = initializationContainer.getWorldEntityBuilder();
        WorldCommandProcessor worldCommandProcessor = initializationContainer.getWorldCommandProcessor();
        WorldManager worldManager = initializationContainer.getWorldManager();
        TerrainManager terrainManager = initializationContainer.getTerrainManager();

        CursorCommandAdaptor cursorStrategyFactory = new CursorCommandAdaptor(
                inputManager,
                cursor2dProvider,
                worldEntityBuilder,
                worldManager,
                terrainManager,
                worldCommandProcessor,
                playerTeam);

        SelectUnitsCursorStrategy selectUnitsCursorStrategy = cursorStrategyFactory.setSelectStrategy();
        worldCursor.setDefaultCursorStrategy(selectUnitsCursorStrategy);
        worldCursor.clearCursorStrategy();
    }
}
