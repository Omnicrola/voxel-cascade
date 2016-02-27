package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.build.ProjectileBuilder;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.SelfDestructControl;

/**
 * Created by Eric on 2/8/2016.
 */
public class LinearProjectileStrategy extends ProjectileStrategy {
    private EntityControlAdapter entityControlAdapter;
    private WeaponDefinition weaponDefinition;
    private ProjectileDefinition projectileDefinition;

    public LinearProjectileStrategy(EntityControlAdapter entityControlAdapter,
                                    WeaponDefinition weaponDefinition,
                                    ProjectileDefinition projectileDefinition) {
        this.entityControlAdapter = entityControlAdapter;
        this.weaponDefinition = weaponDefinition;
        this.projectileDefinition = projectileDefinition;
    }

    @Override
    public Projectile spawnProjectile(Spatial emittingEntity, Vector3f targetLocation) {
        ProjectileBuilder projectileBuilder = this.entityControlAdapter.getProjectileBuilder();
        Projectile projectile = projectileBuilder.build(emittingEntity, this.projectileDefinition);
        addCollisionControl(this.entityControlAdapter.getWorldManager(), entityControlAdapter.getEffectsBuilder(), projectile);
        addRangeControl(projectile);
        addProjectileControl(emittingEntity, targetLocation, projectile);
        return projectile;
    }

    private void addProjectileControl(Spatial emittingEntity, Vector3f targetLocation, Projectile projectile) {
        Vector3f emittingPosition = emittingEntity.getWorldTranslation();
        Vector3f attackVector = targetLocation.subtract(emittingPosition);
        Vector3f velocity = attackVector.normalize().mult(projectileDefinition.getMuzzleVelocity());
        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(projectileDefinition.getSize(), velocity);
        projectile.getSpatial().addControl(linearProjectileControl);
    }

    private void addRangeControl(Projectile projectile) {
        float range = this.weaponDefinition.getRange();
        float projectileLife = range / this.projectileDefinition.getMuzzleVelocity();
        projectile.getSpatial().addControl(new SelfDestructControl(this.entityControlAdapter.getWorldManager(), projectileLife));
    }

}
