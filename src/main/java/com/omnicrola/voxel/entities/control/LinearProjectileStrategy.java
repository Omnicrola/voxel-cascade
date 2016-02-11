package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by Eric on 2/8/2016.
 */
public class LinearProjectileStrategy extends ProjectileStrategy {
    private WeaponDefinition weaponDefinition;
    private ProjectileDefinition projectileDefinition;
    private IGameContainer gameContainer;

    public LinearProjectileStrategy(IGameContainer gameContainer,
                                    WeaponDefinition weaponDefinition,
                                    ProjectileDefinition projectileDefinition) {
        this.gameContainer = gameContainer;
        this.weaponDefinition = weaponDefinition;
        this.projectileDefinition = projectileDefinition;
    }

    @Override
    public Spatial spawnProjectile(Spatial emittingEntity, Vector3f targetLocation) {
        Spatial projectile = this.gameContainer
                .world()
                .build()
                .projectile(emittingEntity, this.projectileDefinition.getId());
        addCollisionControl(this.gameContainer.world(), projectile);
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

}
