package com.omnicrola.voxel.data.units;

import com.omnicrola.voxel.data.WeaponType;

/**
 * Created by Eric on 1/20/2016.
 */
public class WeaponGenerator {
    public static final int ID_CANNON_WEAPON = 1000;
    public static final int ID_MACHINEGUN_WEAPON = 1001;

    public static WeaponDefinition createCannon() {
        WeaponDefinition weaponDefinition = new WeaponDefinition();
        weaponDefinition.globalId = ID_CANNON_WEAPON;
        weaponDefinition.projectileId = ProjectileGenerator.ID_CANNONBALL;
        weaponDefinition.name = "Slug Thrower";
        weaponDefinition.description = "Slightly more effective than garden slugs";
        weaponDefinition.range = 10;
        weaponDefinition.rateOfFire = 60;
        weaponDefinition.weaponType = WeaponType.LINEAR;
        return weaponDefinition;
    }

    public static WeaponDefinition createBbGun() {
        WeaponDefinition weaponDefinition = new WeaponDefinition();
        weaponDefinition.globalId = ID_MACHINEGUN_WEAPON;
        weaponDefinition.projectileId = ProjectileGenerator.ID_SMALL_BULLET;
        weaponDefinition.name = "BB Shooter";
        weaponDefinition.description = "Slightly more effective than garden slugs";
        weaponDefinition.range = 11;
        weaponDefinition.rateOfFire = 240;
        return weaponDefinition;
    }
}
