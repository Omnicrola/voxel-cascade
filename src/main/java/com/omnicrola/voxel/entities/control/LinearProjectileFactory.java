package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.fx.VoxelShowerSpawnAction;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;

/**
 * Created by Eric on 2/8/2016.
 */
public class LinearProjectileFactory implements IProjectileFactory {
    private WeaponDefinition weaponDefinition;
    private ProjectileDefinition projectileDefinition;
    private IGameContainer gameContainer;

    public LinearProjectileFactory(IGameContainer gameContainer,
                                   WeaponDefinition weaponDefinition,
                                   ProjectileDefinition projectileDefinition) {
        this.gameContainer = gameContainer;
        this.weaponDefinition = weaponDefinition;
        this.projectileDefinition = projectileDefinition;
    }

    @Override
    public Spatial spawnProjectile(Spatial emittingEntity, Vector3f targetLocation) {
        IWorldBuilder worldBuilder = this.gameContainer.world().build();
        Spatial projectile = worldBuilder.projectile(emittingEntity, this.projectileDefinition.getId());
        addCollisionControl(projectile);
        addRangeControl(projectile);
        addProjectileControl(emittingEntity, targetLocation, projectile);
        return projectile;
    }

    private void addProjectileControl(Spatial emittingEntity, Vector3f targetLocation, Spatial projectile) {
        Vector3f emittingPosition = emittingEntity.getWorldTranslation();
        Vector3f attackVector = targetLocation.subtract(emittingPosition);
        Vector3f velocity = attackVector.normalize().mult(projectileDefinition.getMuzzleVelocity());
        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(projectileDefinition.getSize(), velocity);
        projectile.addControl(linearProjectileControl);
    }

    private void addRangeControl(Spatial projectile) {
        float range = this.weaponDefinition.getRange();
        float projectileLife = range / this.projectileDefinition.getMuzzleVelocity();
        projectile.addControl(new SelfDestructControl(this.gameContainer.physics(), projectileLife));
    }

    private void addCollisionControl(Spatial projectile) {
        IGameWorld gameWorld = this.gameContainer.world();
        ProjectileCollisionHandler projectileCollisionHandler = new ProjectileCollisionHandler(projectile, gameWorld);
        projectileCollisionHandler.setDeathAction(new VoxelShowerSpawnAction(gameWorld.build(), 100));
        projectile.addControl(new CollisionController(projectileCollisionHandler));
    }
}
