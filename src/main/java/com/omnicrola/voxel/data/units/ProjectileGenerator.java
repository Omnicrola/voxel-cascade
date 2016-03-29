package com.omnicrola.voxel.data.units;

/**
 * Created by Eric on 1/20/2016.
 */
public class ProjectileGenerator {
    public static final int ID_CANNONBALL = 2000;
    public static final int ID_SMALL_BULLET = 2001;

    public static ProjectileDefinition createCannonballProjectile() {
        ProjectileDefinition projectileDefinition = new ProjectileDefinition();
        projectileDefinition.globalId = ID_CANNONBALL;
        projectileDefinition.damage = 10.0f;
        projectileDefinition.obeysGravity = false;
        projectileDefinition.size = 0.125f;
        projectileDefinition.muzzleVelocity = 20f;
        projectileDefinition.model = "sphere12.obj";
        projectileDefinition.texture = "test.bmp";
        projectileDefinition.flightSound = null;
        projectileDefinition.deathSound = new AudioDefinition("cannon-boom4.wav");
        return projectileDefinition;
    }
    public static ProjectileDefinition createSmallBulletProjectile() {
        ProjectileDefinition projectileDefinition = new ProjectileDefinition();
        projectileDefinition.globalId = ID_SMALL_BULLET;
        projectileDefinition.damage = 1.0f;
        projectileDefinition.obeysGravity = false;
        projectileDefinition.size = 0.0125f;
        projectileDefinition.muzzleVelocity = 40f;
        projectileDefinition.model = "sphere12.obj";
        projectileDefinition.texture = "test.bmp";
        return projectileDefinition;
    }
}
