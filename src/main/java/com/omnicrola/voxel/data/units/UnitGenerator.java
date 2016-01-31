package com.omnicrola.voxel.data.units;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.EntityCommand;

/**
 * Created by Eric on 1/20/2016.
 */
public class UnitGenerator {
    public static final int DEFAULT_TANK_ID = 1;

    public static UnitDefinition createDefaultTank() {
        UnitDefinition unitDefinition = new UnitDefinition();
        unitDefinition.globalId = DEFAULT_TANK_ID;
        unitDefinition.name = "Generic Tank AA-11";
        unitDefinition.description = "It resembles every tank, and yet is less than half as effective as less than half of them.";
        unitDefinition.modelGeometry = "tank.obj";
        unitDefinition.modelTexture = "voxel-face-red.png";
        unitDefinition.color = ColorRGBA.Green;
        unitDefinition.hitpoints = 100;
        unitDefinition.weaponId = WeaponGenerator.DEFAULT_WEAPON_ID;
        unitDefinition.weaponEmissionOffset = new Vector3f(0, 0.5f, 0);
        unitDefinition.mass = 1f;
        unitDefinition.movementDefinition = movementDef(1.0f, 0.2f, 0.01f);
        unitDefinition.commands.add(EntityCommand.MOVE);
        unitDefinition.commands.add(EntityCommand.ATTACK);
        unitDefinition.commands.add(EntityCommand.STOP);
        return unitDefinition;
    }

    private static MovementDefinition movementDef(float turnspeed, float maxVelocity, float accelleration) {
        MovementDefinition movementDefinition = new MovementDefinition();
        movementDefinition.turnspeed = turnspeed;
        movementDefinition.maxVelocity = maxVelocity;
        movementDefinition.accelleration = accelleration;
        movementDefinition.personalRadius = 1.0f;
        return movementDefinition;
    }
}
