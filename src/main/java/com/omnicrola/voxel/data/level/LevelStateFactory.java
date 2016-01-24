package com.omnicrola.voxel.data.level;

import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.physics.GroundVehicleControl;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;

import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateFactory {

    public static LevelState create(LevelDefinition levelDefinition, IGameContainer gameContainer) {
        Node terrain = VoxelTerrainGenerator.load(levelDefinition, gameContainer);
        WorldCursor worldCursor = createWorldCursor(gameContainer, terrain);
        DirectionalLight sun = createLights();

        LevelState levelState = new LevelState(terrain, sun, worldCursor, levelDefinition.getName());
        addTeams(levelState, levelDefinition);
        addUnits(levelState, levelDefinition, gameContainer);
        addStructures(levelState, levelDefinition, gameContainer);
        return levelState;
    }

    private static void addTeams(LevelState levelState, LevelDefinition levelDefinition) {
        levelDefinition.getTeams().forEach(t -> levelState.addTeam(new TeamData(t)));
    }

    private static void addStructures(LevelState levelState, LevelDefinition levelDefinition, IGameContainer gameContainer) {
        List<UnitPlacement> structures = levelDefinition.getStructures();
        for (UnitPlacement placement : structures) {
            TeamData teamData = levelState.getTeamById(placement.getTeamId());
            Spatial structure = gameContainer.world().build().structure(placement.getUnitId(), teamData);
            structure.setLocalTranslation(placement.getLocation());
            levelState.addUnit(structure);

        }
    }

    private static void addUnits(LevelState levelState, LevelDefinition levelDefinition, IGameContainer gameContainer) {

        for (UnitPlacement unitPlacement : levelDefinition.getUnitPlacements()) {
            TeamData teamData = levelState.getTeamById(unitPlacement.getTeamId());
            Spatial entity = gameContainer.world().build().unit(unitPlacement.getUnitId(), teamData);
            entity.getControl(GroundVehicleControl.class).setPhysicsLocation(unitPlacement.getLocation());
            levelState.addUnit(entity);
        }
    }

    private static WorldCursor createWorldCursor(IGameContainer gameContainer, Node terrain) {
        WorldCursor worldCursor = gameContainer.world().createCursor(terrain);
        return worldCursor;
    }

    private static DirectionalLight createLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());
        return sun;
    }
}
