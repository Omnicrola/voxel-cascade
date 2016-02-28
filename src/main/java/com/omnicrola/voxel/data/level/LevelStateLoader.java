package com.omnicrola.voxel.data.level;

import com.jme3.renderer.Camera;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.ScreenSelectionEvaluatorFactory;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitMessage;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.world.IWorldNode;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateLoader {

    private VoxelGameEngine voxelGameEngine;
    private ITerrainManager terrainManager;
    private WorldManager worldManager;
    private WorldEntityBuilder worldEntityBuilder;
    private Cursor2dProvider cursor2dProvider;
    private ICommandProcessor commandProcessor;

    public LevelStateLoader(VoxelGameEngine voxelGameEngine,
                            ITerrainManager terrainManager,
                            WorldManager worldManager,
                            WorldEntityBuilder worldEntityBuilder,
                            Cursor2dProvider cursor2dProvider,
                            ICommandProcessor commandProcessor) {
        this.voxelGameEngine = voxelGameEngine;
        this.terrainManager = terrainManager;
        this.worldManager = worldManager;
        this.worldEntityBuilder = worldEntityBuilder;
        this.cursor2dProvider = cursor2dProvider;
        this.commandProcessor = commandProcessor;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        this.terrainManager.load(levelDefinition.getTerrain());

        Camera camera = this.voxelGameEngine.getCamera();
        camera.setRotation(levelDefinition.getCameraOrientation());
        camera.setLocation(levelDefinition.getCameraPosition());

        IGameInput inputManager = new JmeInputWrapper(this.voxelGameEngine.getInputManager(), this.voxelGameEngine.getFlyByCamera());
        IWorldNode worldNode = this.voxelGameEngine.getWorldNode();
        ScreenSelectionEvaluatorFactory screenSelectionEvaluatorFactory = new ScreenSelectionEvaluatorFactory(camera);
        WorldCursor worldCursor = new WorldCursor(inputManager, camera, screenSelectionEvaluatorFactory, worldNode);

        LevelState levelState = new LevelState(worldCursor, levelDefinition.getName());
        addTeams(levelState, levelDefinition);
        addUnits(levelDefinition.getUnitPlacements());
        addStructures(levelDefinition.getStructures());

        CursorCommandDelegator cursorStrategyFactory = new CursorCommandDelegator(
                levelState,
                inputManager,
                this.cursor2dProvider,
                worldCursor,
                this.worldEntityBuilder,
                this.worldManager,
                terrainManager);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = cursorStrategyFactory.setSelectStrategy();
        worldCursor.setDefaultCursorStrategy(selectUnitsCursorStrategy);
        worldCursor.clearCursorStrategy();

        return levelState;
    }

    private void addTeams(LevelState levelState, LevelDefinition levelDefinition) {
        levelDefinition.getTeams().forEach(t -> levelState.addTeam(new TeamData(t)));
    }

    private void addStructures(List<UnitPlacement> structures) {
        for (UnitPlacement placement : structures) {
            this.commandProcessor.addCommand(new SpawnStructureMessage(placement));
        }
    }

    private void addUnits(List<UnitPlacement> unitPlacements) {
        for (UnitPlacement unitPlacement : unitPlacements) {
            this.commandProcessor.addCommand(new SpawnUnitMessage(unitPlacement));
        }
    }

}
