package com.omnicrola.voxel.data.level;

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

    private static LevelDefinition createBasicLevelDefinition() {
        LevelDefinition levelDefinition = new LevelDefinition();
        levelDefinition.name = "Basic";
        levelDefinition.uuid = BASIC_LEVEL_UUID;
        levelDefinition.terrainOffset = new Vec3i(0, -5, 0);
        levelDefinition.terrainSize = new Vec3i(40, 5, 40);
        levelDefinition.unitPlacements = createBasicUnits();
        levelDefinition.structurePlacements = createBasicStructures();
        levelDefinition.teams = createTeams();
        return levelDefinition;
    }

    private static List<UnitPlacement> createBasicStructures() {
        ArrayList<UnitPlacement> unitPlacements = new ArrayList<>();
        unitPlacements.add(createUnitPlacement(StructureGenerator.BUILDING_CORE_ID, PLAYER_TEAM_ID, 15, 0, 0));
        unitPlacements.add(createUnitPlacement(StructureGenerator.BUILDING_CORE_ID, OPPONENT_TEAM_ID, -15, 0, 0));
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

        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, PLAYER_TEAM_ID, 6, 0, -10));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, PLAYER_TEAM_ID, 6, 0, -5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, PLAYER_TEAM_ID, 6, 0, 0));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, PLAYER_TEAM_ID, 6, 0, 5));

        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, OPPONENT_TEAM_ID, -5, 0, -5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, OPPONENT_TEAM_ID, -5, 0, 0));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, OPPONENT_TEAM_ID, -5, 0, 5));
        unitPlacements.add(createUnitPlacement(UnitGenerator.DEFAULT_TANK_ID, OPPONENT_TEAM_ID, -5, 0, 10));
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
