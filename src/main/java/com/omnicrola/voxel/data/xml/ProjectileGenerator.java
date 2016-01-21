package com.omnicrola.voxel.data.xml;

/**
 * Created by Eric on 1/20/2016.
 */
public class ProjectileGenerator {
    public static final int DEFAULT_PROJECTILE_ID = 2000;

    public static ProjectileDefinition createDefaultProjectile() {
        ProjectileDefinition projectileDefinition = new ProjectileDefinition();
        projectileDefinition.globalId = DEFAULT_PROJECTILE_ID;
        projectileDefinition.damage = 1.0f;
        projectileDefinition.obeysGravity = false;
        projectileDefinition.muzzleVelocity = 5;
        projectileDefinition.model = "sphere12.obj";
        projectileDefinition.texture = "test.bmp";
        return projectileDefinition;
    }
}