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

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateFactory {
    public static LevelState create(LevelDefinition levelDefinition, IGameContainer gameContainer) {
        Node terrain = VoxelTerrainGenerator.load(levelDefinition, gameContainer);
        WorldCursor worldCursor = createWorldCursor(gameContainer, terrain);
        DirectionalLight sun = createLights();

        LevelState levelState = new LevelState(terrain, sun, worldCursor, levelDefinition.getName());
        addUnits(levelState, levelDefinition, gameContainer);
        return levelState;
    }

    private static void addUnits(LevelState levelState, LevelDefinition levelDefinition, IGameContainer gameContainer) {

        Map<Integer, TeamData> teamsById = levelDefinition.getTeams()
                .stream()
                .collect(Collectors.toMap(TeamDefinition::getId, t -> new TeamData(t)));
        teamsById.forEach((k, v) -> levelState.addTeam(v));

        for (UnitPlacement unitPlacement : levelDefinition.getUnitPlacements()) {
            TeamData teamData = teamsById.get(unitPlacement.getTeamId());
            Spatial entity = gameContainer.world().build().unit(unitPlacement.getUnitId(), teamData);
            entity.getControl(GroundVehicleControl.class).setPhysicsLocation(unitPlacement.getLocation());
            entity.setLocalTranslation(unitPlacement.getLocation());
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
