package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by Eric on 2/8/2016.
 */
public interface IProjectileStrategy {
    Spatial spawnProjectile(Spatial emittingEntity, Vector3f targetLocation);
}
