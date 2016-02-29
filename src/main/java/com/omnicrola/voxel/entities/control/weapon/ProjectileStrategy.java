package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.control.collision.CollisionController;
import com.omnicrola.voxel.entities.control.collision.ProjectileCollisionHandler;
import com.omnicrola.voxel.fx.VoxelShowerSpawnAction;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by Eric on 2/9/2016.
 */
public abstract class ProjectileStrategy implements IProjectileStrategy {

    protected void addCollisionControl(WorldManager worldManager, ParticleBuilder effectsBuilder, Projectile projectile) {
        Spatial spatial = projectile.getSpatial();
        ProjectileCollisionHandler projectileCollisionHandler = new ProjectileCollisionHandler(spatial, worldManager);
        projectileCollisionHandler.setDeathAction(new VoxelShowerSpawnAction(effectsBuilder, 100));
        spatial.addControl(new CollisionController(projectileCollisionHandler));
    }
}
