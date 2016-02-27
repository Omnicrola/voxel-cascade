package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Projectile;

/**
 * Created by Eric on 2/8/2016.
 */
public interface IProjectileStrategy {
    Projectile spawnProjectile(Spatial emittingEntity, Vector3f targetLocation);
}
