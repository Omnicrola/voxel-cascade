package com.omnicrola.voxel.data.level;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.units.StructureGenerator;
import com.omnicrola.voxel.data.units.UnitGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Eric on 1/20/2016.
 */
public class LevelGeneratorTool {

    public static final UUID BASIC_LEVEL_UUID = UUID.fromString("4ae9ecf5-25e0-477a-9662-46dda8f095c9");
    public static final int PLAYER_TEAM_ID = 1;
    public static final int OPPONENT_TEAM_ID = 2;

    public static void main(String[] args) throws FileNotFoundException {
        GameXmlDataParser gameXmlDataParser = new GameXmlDataParser();
        File outputFile = new File("src/assets/Data/levels/basic.level");
        LevelDefinition levelDefinition = createBasicLevelDefinition();
        gameXmlDataParser.writeLevel(new FileOutputStream(outputFile), levelDefinition);
    }

    public static LevelDefinition createBasicLevelDefinition() {
        LevelDefinition levelDefinition = new LevelDefinition();
        levelDefinition.name = "Basic";
        levelDefinition.uuid = BASIC_LEVEL_UUID;
        levelDefinition.cameraPosition = new Vector3f(17.240665f, 5.812654f, 15.605931f);
        levelDefinition.cameraOrientation = new Quaternion(-0.04802279f, 0.94986904f, -0.24847628f, -0.18358125f);
        levelDefinition.terrain.terrainOffset = new Vec3i(0, -1, 0);
        levelDefinition.terrain.width = 100;
        levelDefinition.terrain.depth = 100;
        levelDefinition.terrain.verticalScale = 15;
        levelDefinition.terrain.octaves = 8;
        levelDefinition.terrain.seed = 12345;
        levelDefinition.unitPlacements = createBasicUnits();
        levelDefinition.structurePlacements = createBasicStructures();
        levelDefinition.teams = createTeams();
        return levelDefinition;
    }

    private static List<UnitPlacement> createBasicStructures() {
        ArrayList<UnitPlacement> unitPlacements = new ArrayList<>();
        unitPlacements.add(createUnitPlacement(StructureGenerator.BUILDING_PLAYER_CORE_ID, PLAYER_TEAM_ID, 20, 2f, 0));
        unitPlacements.add(createUnitPlacement(StructureGenerator.BUILDING_ENEMY_CORE_ID, OPPONENT_TEAM_ID, -20, 2f, 0));
        return unitPlacements;
    }

    private static List<TeamDefinition> createTeams() {
        ArrayList<TeamDefinition> teamDefinitions = new ArrayList<>();
        teamDefinitions.add(createTeam(PLAYER_TEAM_ID));
        teamDefinitions.add(createTeam(OPPONENT_TEAM_ID));
        return teamDefinitions;
    }

    private static TeamDefinition createTeam(int id) {
        TeamDefinition teamDefinition = new TeamDefinition();
        teamDefinition.id = id;
        return teamDefinition;
    }

    private static ArrayList<UnitPlacement> createBasicUnits() {
        ArrayList<UnitPlacement> unitPlacements = new ArrayList<>();

        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_RED_TANK, PLAYER_TEAM_ID, 6, 10, -10));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_RED_TANK, PLAYER_TEAM_ID, 6, 10, -5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_RED_TANK, PLAYER_TEAM_ID, 6, 10, 0));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_RED_TANK, PLAYER_TEAM_ID, 6, 10, 5));

        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_BLUE_TANK, OPPONENT_TEAM_ID, 1, 10, -5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_BLUE_TANK, OPPONENT_TEAM_ID, 1, 10, 0));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_BLUE_TANK, OPPONENT_TEAM_ID, 1, 10, 5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.ID_BLUE_TANK, OPPONENT_TEAM_ID, 1, 10, 10));
        return unitPlacements;
    }

    private static UnitPlacement createUnitPlacement(int unitGlobalId, int teamId, float x, float y, float z) {
        UnitPlacement unitPlacement = new UnitPlacement();
        unitPlacement.unitId = unitGlobalId;
        unitPlacement.teamId = teamId;
        unitPlacement.location = new Vector3f(x, y, z);
        return unitPlacement;
    }
}
