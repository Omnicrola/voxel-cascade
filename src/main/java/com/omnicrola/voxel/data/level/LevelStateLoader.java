package com.omnicrola.voxel.data.level;

import com.jme3.renderer.Camera;
import com.omnicrola.voxel.commands.IMessageProcessor;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelTerrainState;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.ScreenSelectionEvaluatorFactory;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitMessage;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.data.VoxelType;
import com.omnicrola.voxel.world.IWorldNode;
import com.omnicrola.voxel.world.WorldEntityBuilder;
import com.omnicrola.voxel.world.WorldManager;

import java.util.Arrays;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateLoader {

    private VoxelGameEngine voxelGameEngine;
    private IMessageProcessor messageProcessor;
    private VoxelTerrainState voxelTerrainState;
    private WorldManager worldManager;
    private WorldEntityBuilder worldEntityBuilder;

    public LevelStateLoader(VoxelGameEngine voxelGameEngine,
                            IMessageProcessor messageProcessor,
                            VoxelTerrainState voxelTerrainState,
                            WorldManager worldManager,
                            WorldEntityBuilder worldEntityBuilder) {
        this.voxelGameEngine = voxelGameEngine;
        this.messageProcessor = messageProcessor;
        this.voxelTerrainState = voxelTerrainState;
        this.worldManager = worldManager;
        this.worldEntityBuilder = worldEntityBuilder;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        this.voxelTerrainState.load(levelDefinition.getTerrain());

        Camera camera = this.voxelGameEngine.getCamera();
        IGameInput inputManager = new JmeInputWrapper(this.voxelGameEngine.getInputManager(), this.voxelGameEngine.getFlyByCamera());
        IWorldNode worldNode = this.voxelGameEngine.getWorldNode();
        ScreenSelectionEvaluatorFactory screenSelectionEvaluatorFactory = new ScreenSelectionEvaluatorFactory(camera);
        WorldCursor worldCursor = new WorldCursor(inputManager, camera, screenSelectionEvaluatorFactory, worldNode);

        LevelState levelState = new LevelState(worldCursor, levelDefinition.getName());
        addTeams(levelState, levelDefinition);
        addUnits(levelDefinition.getUnitPlacements());
        addStructures(levelDefinition.getStructures());

        CursorCommandDelegator cursorStrategyFactory = new CursorCommandDelegator(levelState, inputManager, worldCursor, this.worldEntityBuilder, this.worldManager);
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
            this.messageProcessor.sendLocal(new SpawnStructureMessage(placement));
        }
    }

    private void addUnits(List<UnitPlacement> unitPlacements) {
        for (UnitPlacement unitPlacement : unitPlacements) {
            this.messageProcessor.sendLocal(new SpawnUnitMessage(unitPlacement));
        }
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }
}
