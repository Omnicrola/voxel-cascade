package com.omnicrola.voxel.data.level;

import com.jme3.renderer.Camera;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.StartLevelCommand;
import com.omnicrola.voxel.data.ILevelManager;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.AnnihilationWinConditionState;
import com.omnicrola.voxel.network.messages.SpawnStructureMessage;
import com.omnicrola.voxel.network.messages.SpawnUnitCommand;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.world.WorldManager;

import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateLoader {

    private VoxelGameEngine voxelGameEngine;
    private ITerrainManager terrainManager;
    private WorldManager worldManager;
    private ICommandProcessor commandProcessor;
    private ILevelManager levelManager;

    public LevelStateLoader(VoxelGameEngine voxelGameEngine,
                            ITerrainManager terrainManager,
                            WorldManager worldManager,
                            ICommandProcessor commandProcessor,
                            ILevelManager levelManager) {
        this.voxelGameEngine = voxelGameEngine;
        this.terrainManager = terrainManager;
        this.worldManager = worldManager;
        this.commandProcessor = commandProcessor;
        this.levelManager = levelManager;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        this.terrainManager.load(levelDefinition.getTerrain());

        Camera camera = this.voxelGameEngine.getCamera();
        camera.setRotation(levelDefinition.getCameraOrientation());
        camera.setLocation(levelDefinition.getCameraPosition());

        LevelState levelState = new LevelState(levelDefinition.getName());
        addTeams(levelState, levelDefinition);
        addUnits(levelDefinition.getUnitPlacements());
        addStructures(levelDefinition.getStructures());

        AnnihilationWinConditionState winCondition = new AnnihilationWinConditionState(this.worldManager, this.levelManager);
        this.voxelGameEngine.getStateManager().attach(winCondition);
        this.commandProcessor.addCommand(new StartLevelCommand());

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
            this.commandProcessor.addCommand(new SpawnUnitCommand(unitPlacement));
        }
    }

}
