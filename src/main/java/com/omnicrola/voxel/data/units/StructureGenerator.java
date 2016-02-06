package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.BuildUnitCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.entities.control.AutomatedWeaponControlFactory;

/**
 * Created by omnic on 1/24/2016.
 */
public class StructureGenerator {
    public static final int BUILDING_PLAYER_CORE_ID = 1;
    public static final int BUILDING_ENEMY_CORE_ID = 2;
    public static final int ID_TURRET = 100;

    public static StructureDefinition createPlayerCoreBuilding() {
        StructureDefinition structureDefinition = createCore();
        structureDefinition.modelTexture = "voxel-face-green.png";
        structureDefinition.globalId = BUILDING_PLAYER_CORE_ID;
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
        structureDefinition.commands.add(EntityCommand.BUILD);
        structureDefinition.buildCommands.add(new BuildUnitCommand(UnitGenerator.ID_RED_TANK));
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
        structureDefinition.controlFactories.add(new AutomatedWeaponControlFactory() {{
            this.weaponId = WeaponGenerator.ID_CANNON_WEAPON;
            this.weaponOffset = new Vector3f(0, 1, 0);
        }});
        return structureDefinition;
    }
}
