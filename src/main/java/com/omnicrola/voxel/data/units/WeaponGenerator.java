package com.omnicrola.voxel.data.units;

/**
 * Created by Eric on 1/20/2016.
 */
public class WeaponGenerator {
    public static final int DEFAULT_WEAPON_ID = 1000;

    public static WeaponDefinition createDefaultWeapon() {
        WeaponDefinition weaponDefinition = new WeaponDefinition();
        weaponDefinition.globalId = DEFAULT_WEAPON_ID;
        weaponDefinition.projectileId = ProjectileGenerator.DEFAULT_PROJECTILE_ID;
        weaponDefinition.name = "Slug Thrower";
        weaponDefinition.description = "Slightly more effective than garden slugs";
        weaponDefinition.range = 10;
        weaponDefinition.rateOfFire = 60;
        return weaponDefinition;
    }
}
