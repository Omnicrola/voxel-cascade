package com.omnicrola.voxel.data.units;

import com.omnicrola.voxel.data.GameXmlDataParser;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 1/20/2016.
 */
public class UnitDefinitionGeneratorTool {

    public static void main(String[] args) throws Exception {
        File outputFile = new File("src/assets/Data/core-definitions.units");
        GameXmlDataParser gameXmlDataParser = new GameXmlDataParser();
        FileOutputStream stream = new FileOutputStream(outputFile);

        XmlGameDefinitions coreDefinitions = createCoreDefinitions();
        gameXmlDataParser.writeDefinitions(stream, coreDefinitions);
    }

    private static XmlGameDefinitions createCoreDefinitions() {
        XmlGameDefinitions xmlGameDefinitions = new XmlGameDefinitions();
        xmlGameDefinitions.units = createUnits();
        xmlGameDefinitions.structures = createStructures();
        xmlGameDefinitions.weapons = createWeapons();
        xmlGameDefinitions.projectiles = createProjectiles();
        return xmlGameDefinitions;
    }

    private static ArrayList<StructureDefinition> createStructures() {
        ArrayList<StructureDefinition> structures = new ArrayList<>();
        structures.add(StructureGenerator.createPlayerCoreBuilding());
        structures.add(StructureGenerator.createEnemyCoreBuilding());
        structures.add(StructureGenerator.createTurret());
        return structures;
    }

    private static List<UnitDefinition> createUnits() {
        ArrayList<UnitDefinition> unitDefinitions = new ArrayList<>();
        unitDefinitions.add(UnitGenerator.createRedTank());
        unitDefinitions.add(UnitGenerator.createGreenTank());
        unitDefinitions.add(UnitGenerator.createBlueTank());
        return unitDefinitions;
    }

    private static List<WeaponDefinition> createWeapons() {
        ArrayList<WeaponDefinition> weaponDefinitions = new ArrayList<>();
        weaponDefinitions.add(WeaponGenerator.createCannon());
        weaponDefinitions.add(WeaponGenerator.createBbGun());
        return weaponDefinitions;
    }

    private static List<ProjectileDefinition> createProjectiles() {
        ArrayList<ProjectileDefinition> projectileDefinitions = new ArrayList<>();
        projectileDefinitions.add(ProjectileGenerator.createCannonballProjectile());
        projectileDefinitions.add(ProjectileGenerator.createSmallBulletProjectile());
        return projectileDefinitions;
    }
}
