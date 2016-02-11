package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.BuildVoxelCommand;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 1/20/2016.
 */
public class UnitGenerator {
    public static final int ID_RED_TANK = 1;
    public static final int ID_GREEN_TANK = 2;
    public static final int ID_BLUE_TANK = 3;

    public static UnitDefinition createBlueTank() {
        UnitDefinition unitDefinition = buildTank();
        unitDefinition.globalId = ID_BLUE_TANK;
        unitDefinition.name = "Blue Tank";
        unitDefinition.modelTexture = "voxel-face-blue.png";
        return unitDefinition;
    }

    public static UnitDefinition createGreenTank() {
        UnitDefinition unitDefinition = buildTank();
        unitDefinition.globalId = ID_GREEN_TANK;
        unitDefinition.weaponId = WeaponGenerator.ID_MACHINEGUN_WEAPON;
        unitDefinition.name = "Green Tank";
        unitDefinition.modelTexture = "voxel-face-green.png";
        return unitDefinition;
    }

    public static UnitDefinition createRedTank() {
        UnitDefinition unitDefinition = buildTank();
        unitDefinition.globalId = ID_RED_TANK;
        unitDefinition.name = "Red Tank";
        unitDefinition.modelTexture = "voxel-face-red.png";
        return unitDefinition;
    }

    private static UnitDefinition buildTank() {
        UnitDefinition unitDefinition = new UnitDefinition();
        unitDefinition.description = "It resembles every tank, and yet is less than half as effective as less than half of them.";
        unitDefinition.modelGeometry = "tank.obj";
        unitDefinition.color = ColorRGBA.Green;
        unitDefinition.hitpoints = 100;
        unitDefinition.weaponId = WeaponGenerator.ID_CANNON_WEAPON;
        unitDefinition.weaponEmissionOffset = new Vector3f(0, 0.5f, 0);
        unitDefinition.mass = 1f;
        unitDefinition.movementDefinition = movementDef(25f, 0.1f);
        unitDefinition.commands.add(EntityCommand.MOVE);
        unitDefinition.commands.add(EntityCommand.ATTACK);
        unitDefinition.commands.add(EntityCommand.STOP);
        unitDefinition.commands.add(EntityCommand.HARVEST);
        unitDefinition.commands.add(EntityCommand.BUILD);
        unitDefinition.buildCommands.add(new BuildVoxelCommand(GameConstants.TERRAIN_TYPE_1));
        return unitDefinition;
    }

    private static MovementDefinition movementDef(float maxVelocity, float turnspeed) {
        MovementDefinition movementDefinition = new MovementDefinition();
        movementDefinition.turnspeed = turnspeed;
        movementDefinition.maxVelocity = maxVelocity;
        movementDefinition.personalRadius = 1.0f;
        return movementDefinition;
    }
}
