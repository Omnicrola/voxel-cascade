package com.omnicrola.voxel.data.level;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.physics.GroundVehicleControl;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;

import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public class LevelStateFactory {

    private VoxelTerrainGenerator voxelTerrainGenerator;
    private IGameContainer gameContainer;

    public LevelStateFactory(VoxelTerrainGenerator voxelTerrainGenerator,
                             IGameContainer gameContainer) {
        this.voxelTerrainGenerator = voxelTerrainGenerator;
        this.gameContainer = gameContainer;
    }

    public LevelState create(LevelDefinition levelDefinition) {
        Node terrain = this.voxelTerrainGenerator.load(levelDefinition);
        WorldCursor worldCursor = createWorldCursor(terrain);

        LevelState levelState = new LevelState(terrain, worldCursor, levelDefinition.getName());
        addLights(levelState, levelDefinition);
        addTeams(levelState, levelDefinition);
        addUnits(levelState, levelDefinition);
        addStructures(levelState, levelDefinition);

        CursorCommandDelegator cursorStrategyFactory = new CursorCommandDelegator(this.gameContainer, levelState, worldCursor);
        SelectUnitsCursorStrategy selectUnitsCursorStrategy = cursorStrategyFactory.setSelectStrategy();
        worldCursor.setDefaultCursorStrategy(selectUnitsCursorStrategy);
        worldCursor.clearCursorStrategy();

        return levelState;
    }

    private static void addLights(LevelState levelState, LevelDefinition levelDefinition) {
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White.mult(0.7f));
        sun.setDirection(new Vector3f(-0.5f, -0.5f, -0.5f).normalizeLocal());

        AmbientLight ambientLight = new AmbientLight();
        ambientLight.setColor(ColorRGBA.White.mult(0.3f));

        levelState.addLight(sun);
        levelState.addLight(ambientLight);
    }

    private void addTeams(LevelState levelState, LevelDefinition levelDefinition) {
        levelDefinition.getTeams().forEach(t -> levelState.addTeam(new TeamData(t)));
    }

    private void addStructures(LevelState levelState, LevelDefinition levelDefinition) {
        List<UnitPlacement> structures = levelDefinition.getStructures();
        for (UnitPlacement placement : structures) {
            TeamData teamData = levelState.getTeamById(placement.getTeamId());
            Spatial structure = this.gameContainer.world().build().structure(placement.getUnitId(), teamData);
            structure.getControl(RigidBodyControl.class).setPhysicsLocation(placement.getLocation());
            levelState.addUnit(structure);
        }
    }

    private void addUnits(LevelState levelState, LevelDefinition levelDefinition) {
        for (UnitPlacement unitPlacement : levelDefinition.getUnitPlacements()) {
            TeamData teamData = levelState.getTeamById(unitPlacement.getTeamId());
            Spatial entity = this.gameContainer.world().build().unit(unitPlacement.getUnitId(), teamData);
            entity.getControl(GroundVehicleControl.class).setPhysicsLocation(unitPlacement.getLocation());
            levelState.addUnit(entity);
        }
    }

    private WorldCursor createWorldCursor(Node terrain) {
        WorldCursor worldCursor = this.gameContainer.world().createCursor(terrain);
        return worldCursor;
    }
}
