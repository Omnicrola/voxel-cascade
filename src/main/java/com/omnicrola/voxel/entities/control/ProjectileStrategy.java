package com.omnicrola.voxel.entities.control;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.fx.VoxelShowerSpawnAction;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by Eric on 2/9/2016.
 */
public abstract class ProjectileStrategy implements IProjectileStrategy{

    protected void addCollisionControl(IGameWorld gameWorld, Spatial projectile) {
        ProjectileCollisionHandler projectileCollisionHandler = new ProjectileCollisionHandler(projectile, gameWorld);
        projectileCollisionHandler.setDeathAction(new VoxelShowerSpawnAction(gameWorld.build(), 100));
        projectile.addControl(new CollisionController(projectileCollisionHandler));
    }
}
