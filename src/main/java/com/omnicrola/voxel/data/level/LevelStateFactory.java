package com.omnicrola.voxel.data.level;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.debug.DebugOrientationControl;
import com.omnicrola.voxel.debug.DebugVelocityControl;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
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
public class LevelStateFactory {

    private OldVoxelTerrainGenerator voxelTerrainGenerator;
    private IGameContainer gameContainer;

    public LevelStateFactory(OldVoxelTerrainGenerator voxelTerrainGenerator,
                             IGameContainer gameContainer) {
        this.voxelTerrainGenerator = voxelTerrainGenerator;
        this.gameContainer = gameContainer;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        VoxelTypeLibrary voxelTypeLibrary = buildVoxelTypeLibrary();
        Node terrain = this.voxelTerrainGenerator.load(levelDefinition, voxelTypeLibrary);
        WorldCursor worldCursor = createWorldCursor(terrain);

        LevelState levelState = new LevelState(terrain, worldCursor, levelDefinition.getName(), voxelTypeLibrary);
        addTeams(levelState, levelDefinition);
        addUnits(levelState, levelDefinition);
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

    private void addStructures(LevelState levelState, LevelDefinition levelDefinition) {
        VoxelTerrainControl terrainControl = levelState.getTerrainNode().getControl(VoxelTerrainControl.class);
        List<UnitPlacement> structures = levelDefinition.getStructures();
        for (UnitPlacement placement : structures) {
            TeamData teamData = levelState.getTeamById(placement.getTeamId());
            Spatial structure = this.gameContainer.world().build().structure(placement.getUnitId(), teamData);
            Vector3f position = terrainControl.findLowestNonSolidVoxel(placement.getLocation());
            structure.getControl(RigidBodyControl.class).setPhysicsLocation(position);
            levelState.addEntity(structure);
        }
    }

    private void addUnits(LevelState levelState, LevelDefinition levelDefinition) {
        VoxelTerrainControl terrainControl = levelState.getTerrainNode().getControl(VoxelTerrainControl.class);
        for (UnitPlacement unitPlacement : levelDefinition.getUnitPlacements()) {
            TeamData teamData = levelState.getTeamById(unitPlacement.getTeamId());
            Spatial entity = this.gameContainer.world().build().unit(unitPlacement.getUnitId(), teamData);
            Vector3f position = terrainControl.findLowestNonSolidVoxel(unitPlacement.getLocation());
            entity.getControl(GroundVehicleControl.class).setPhysicsLocation(position);
            entity.setLocalTranslation(position);
            levelState.addEntity(entity);

//            addOrientationArrow(levelState, entity);
//            addVelocityArrow(levelState, entity);
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

    private WorldCursor createWorldCursor(Node terrain) {
        WorldCursor worldCursor = this.gameContainer.world().createCursor(terrain);
        return worldCursor;
    }

    private VoxelTypeLibrary buildVoxelTypeLibrary() {
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return voxelTypeLibrary;
    }
}
