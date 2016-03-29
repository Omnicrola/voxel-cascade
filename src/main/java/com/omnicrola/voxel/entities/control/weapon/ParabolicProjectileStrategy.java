package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.entities.build.ProjectileBuilder;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.entities.control.SelfDestructControl;

/**
 * Created by Eric on 2/8/2016.
 */
public class ParabolicProjectileStrategy extends ProjectileStrategy {

    private WeaponDefinition weaponDefinition;

    public ParabolicProjectileStrategy(EntityControlAdapter entityControlAdapter,
                                       WeaponDefinition weaponDefinition,
                                       ProjectileDefinition projectileDefinition) {
        super(entityControlAdapter, projectileDefinition);
        this.weaponDefinition = weaponDefinition;
    }

    @Override
    public Projectile spawnProjectile(Spatial emittingEntity, Vector3f projectileOffset, Vector3f targetLocation) {
        ProjectileBuilder projectileBuilder = this.entityControlAdapter.getProjectileBuilder();
        Projectile projectile = projectileBuilder.build(emittingEntity, this.projectileDefinition);

        addCollisionControl(projectile);
        projectile.getSpatial().addControl(createProjectileControl(emittingEntity, targetLocation));
        projectile.getSpatial().addControl(createSelfDestruct());
        return projectile;
    }

    private Control createSelfDestruct() {
        return new SelfDestructControl(this.entityControlAdapter.getWorldManager(), 5);
    }

    private ParabolicProjectileControl createProjectileControl(Spatial emittingEntity,
                                                               Vector3f targetLocation) {
        Vector3f gravity = new Vector3f(0, -9.8f, 0);
        final Vector3f trajectory = calculateParabolicTrajectory(emittingEntity, targetLocation);
        ParabolicProjectileControl parabolicProjectileControl = new ParabolicProjectileControl(0.125f, trajectory, gravity);
        return parabolicProjectileControl;
    }

    private Vector3f calculateParabolicTrajectory(Spatial emittingEntity, Vector3f targetLocation) {
        Vector3f startPosition = emittingEntity.getWorldTranslation();
        Vector3f delta = targetLocation.subtract(startPosition);
        float muzzleVelocity = this.projectileDefinition.getMuzzleVelocity();

        final float d = (float) Math.sqrt(sq(delta.x) + sq(delta.y)
                + sq(delta.z));
        final float vX = delta.x / d * muzzleVelocity;
        final float vY = delta.y / d * muzzleVelocity;
        final float vZ = delta.z / d * muzzleVelocity;

        return new Vector3f(vX, vY, vZ);
    }

    private static float sq(float n) {
        return n * n;
    }
}
