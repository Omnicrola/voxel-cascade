package com.omnicrola.voxel.data.level;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.input.InputManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.commands.IMessageProcessor;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.debug.DebugOrientationControl;
import com.omnicrola.voxel.debug.DebugVelocityControl;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelTerrainState;
import com.omnicrola.voxel.engine.states.WorldManagerState;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameInput;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitMessage;
import com.omnicrola.voxel.physics.GroundVehicleControl;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;
import com.omnicrola.voxel.terrain.OldVoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Arrays;
import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateLoader {

    private VoxelGameEngine voxelGameEngine;
    private IMessageProcessor messageProcessor;
    private VoxelTerrainState voxelTerrainState;

    public LevelStateLoader(VoxelGameEngine voxelGameEngine,
                            IMessageProcessor messageProcessor,
                            VoxelTerrainState voxelTerrainState) {
        this.voxelGameEngine = voxelGameEngine;
        this.messageProcessor = messageProcessor;
        this.voxelTerrainState = voxelTerrainState;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();
        this.voxelTerrainState.load(levelDefinition.getTerrain());

        Camera camera = this.voxelGameEngine.getCamera();
        IGameInput inputManager = new JmeInputWrapper(this.voxelGameEngine.getInputManager(), this.voxelGameEngine.getFlyByCamera());
        Node terrainNode = this.voxelGameEngine.getWorldNode().getTerrainNode();
        WorldCursor worldCursor = new WorldCursor(inputManager, camera, terrainNode);

        LevelState levelState = new LevelState(worldCursor, levelDefinition.getName(), voxelTypeLibrary);
        addTeams(levelState, levelDefinition);
        addUnits(levelDefinition.getUnitPlacements());
        addStructures(levelState, levelDefinition);

        CursorCommandDelegator cursorStrategyFactory = new CursorCommandDelegator(this.gameContainer, levelState, worldCursor);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = cursorStrategyFactory.setSelectStrategy();
        worldCursor.setDefaultCursorStrategy(selectUnitsCursorStrategy);
        worldCursor.clearCursorStrategy();

        return levelState;
    }

    private void addTeams(LevelState levelState, LevelDefinition levelDefinition) {
        levelDefinition.getTeams().forEach(t -> levelState.addTeam(new TeamData(t)));
    }

    private void addStructures(List<UnitPlacement> structures ) {
        for (UnitPlacement placement : structures) {
            this.messageProcessor.sendLocal(new SpawnStructureMessage(placement));
        }
    }

    private void addUnits(List<UnitPlacement> unitPlacements) {
        for (UnitPlacement unitPlacement : unitPlacements) {
            this.messageProcessor.sendLocal(new SpawnUnitMessage(unitPlacement));
        }
    }

    private void addOrientationArrow(LevelState levelState, Spatial entity) {
        Spatial arrow = this.gameContainer.world().build().arrow(Vector3f.UNIT_Z, ColorRGBA.Blue);
        arrow.addControl(new DebugOrientationControl(entity));
        levelState.addEntity(arrow);
    }

    private void addVelocityArrow(LevelState levelState, Spatial entity) {
        Spatial arrow = this.gameContainer.world().build().arrow(Vector3f.UNIT_Z, ColorRGBA.Green);
        arrow.addControl(new DebugVelocityControl(entity));
        levelState.addEntity(arrow);
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }
}
