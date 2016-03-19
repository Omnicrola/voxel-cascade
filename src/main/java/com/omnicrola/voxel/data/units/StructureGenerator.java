package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.BuildUnitCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.entities.control.AutomatedWeaponControlFactory;
import com.omnicrola.voxel.entities.control.fx.ParticleEffectControlFactory;
import com.omnicrola.voxel.entities.control.move.RotationControlFactory;
import com.omnicrola.voxel.entities.control.resources.PassiveHarvestControlFactory;

/**
 * Created by omnic on 1/24/2016.
 */
public class StructureGenerator {
    public static final int BUILDING_PLAYER_CORE_ID = 1;
    public static final int BUILDING_ENEMY_CORE_ID = 2;
    public static final int ID_TURRET = 100;
    public static final int ID_EXTRACTOR = 101;
    public static final int ID_TANK_FACTORY = 102;

    public static StructureDefinition createPlayerCoreBuilding() {
        StructureDefinition structureDefinition = createCore();
        structureDefinition.modelTexture = "voxel-face-green.png";
        structureDefinition.globalId = BUILDING_PLAYER_CORE_ID;
        return structureDefinition;
    }

    public static StructureDefinition createExtractor() {
        StructureDefinition structureDefinition = new StructureDefinition();
        structureDefinition.globalId = ID_EXTRACTOR;
        structureDefinition.modelTexture = "voxel-face-green.png";
        structureDefinition.modelGeometry = "extractor.obj";
        structureDefinition.hitpoints = 100;
        structureDefinition.name = "Resource Extractor";
        structureDefinition.buildCost = 5;
        structureDefinition.description = "Extracts resources from underlying cubes";
        structureDefinition.controlFactories.add(new RotationControlFactory(new Vector3f(0, 1, 0), 1f));
        structureDefinition.controlFactories.add(new ParticleEffectControlFactory());
        structureDefinition.controlFactories.add(new PassiveHarvestControlFactory(1f, 3f));
        return structureDefinition;
    }

    public static StructureDefinition createEnemyCoreBuilding() {
        StructureDefinition structureDefinition = createCore();
        structureDefinition.globalId = BUILDING_ENEMY_CORE_ID;
        structureDefinition.modelTexture = "voxel-face-red.png";
        return structureDefinition;
    }

    private static StructureDefinition createCore() {
        StructureDefinition structureDefinition = new StructureDefinition();
        structureDefinition.color = ColorRGBA.Blue;
        structureDefinition.modelGeometry = "building-core.obj";
        structureDefinition.name = "Core";
        structureDefinition.description = "Use this to build things";
        structureDefinition.hitpoints = 500;
        structureDefinition.buildCost = 100;
        structureDefinition.commands.add(EntityCommand.BUILD);
        structureDefinition.buildCommands.add(new BuildUnitCommand("Builder", UnitGenerator.ID_BUILDER, 10f));
        return structureDefinition;
    }

    public static StructureDefinition createTurret() {
        StructureDefinition structureDefinition = new StructureDefinition();
        structureDefinition.globalId = ID_TURRET;
        structureDefinition.modelGeometry = "turret.obj";
        structureDefinition.modelTexture = "voxel-face-red.png";
        structureDefinition.name = "Turret";
        structureDefinition.description = "Static defense";
        structureDefinition.hitpoints = 250;
        structureDefinition.buildCost = 10;
        structureDefinition.controlFactories.add(new AutomatedWeaponControlFactory() {{
            this.weaponId = WeaponGenerator.ID_CANNON_WEAPON;
            this.weaponOffset = new Vector3f(0, 1, 0);
        }});
        return structureDefinition;
    }

    public static StructureDefinition createTankFactory() {
        StructureDefinition structureDefinition = new StructureDefinition();
        structureDefinition.globalId = ID_TANK_FACTORY;
        structureDefinition.modelGeometry = "builder-unit.obj";
        structureDefinition.modelTexture = "companion-cube.jpg";
        structureDefinition.name = "Tank Factory";
        structureDefinition.description = "Roll out!";
        structureDefinition.hitpoints = 250;
        structureDefinition.buildCost = 10;
        structureDefinition.commands.add(EntityCommand.BUILD);
        structureDefinition.buildCommands.add(new BuildUnitCommand("Tank", UnitGenerator.ID_RED_TANK, 5f));
        return structureDefinition;
    }
}
