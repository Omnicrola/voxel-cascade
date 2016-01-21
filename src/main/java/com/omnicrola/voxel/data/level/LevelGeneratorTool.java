package com.omnicrola.voxel.data.level;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.GameXmlDataParser;
import com.omnicrola.voxel.data.units.UnitGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Eric on 1/20/2016.
 */
public class LevelGeneratorTool {

    public static final UUID BASIC_LEVEL_UUID = UUID.fromString("4ae9ecf5-25e0-477a-9662-46dda8f095c9");

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
        return levelDefinition;
    }

    private static ArrayList<UnitPlacement> createBasicUnits() {
        ArrayList<UnitPlacement> unitPlacements = new ArrayList<>();
        unitPlacements.add(createUnitPlacement());
        return unitPlacements;
    }

    private static UnitPlacement createUnitPlacement() {
        UnitPlacement unitPlacement = new UnitPlacement();
        unitPlacement.unitId = UnitGenerator.DEFAULT_TANK_ID;
        unitPlacement.location = new Vector3f(0, 0, 0);
        return unitPlacement;
    }
}
