package com.omnicrola.voxel.entities.control.old;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import com.omnicrola.voxel.data.units.ProjectileDefinition;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;

/**
 * Created by Eric on 2/8/2016.
 */
public class ParabolicProjectileStrategy extends ProjectileStrategy {

    private WeaponDefinition weaponDefinition;
    private ProjectileDefinition projectileDefinition;
    private IGameContainer gameContainer;

    public ParabolicProjectileStrategy(IGameContainer gameContainer,
                                       WeaponDefinition weaponDefinition,
                                       ProjectileDefinition projectileDefinition
    ) {
        this.weaponDefinition = weaponDefinition;
        this.projectileDefinition = projectileDefinition;
        this.gameContainer = gameContainer;
    }

    @Override
    public Spatial spawnProjectile(Spatial emittingEntity, Vector3f targetLocation) {
        Spatial projectile = this.gameContainer
                .world()
                .build()
                .projectile(emittingEntity, this.projectileDefinition.getId());
        addCollisionControl(this.gameContainer.world(), projectile);
        projectile.addControl(createProjectileControl(emittingEntity, targetLocation));
        projectile.addControl(createSelfDestruct());
        return projectile;
    }

    private Control createSelfDestruct() {
        return new SelfDestructControl(this.gameContainer.physics(), 5);
    }

    private ParabolicProjectileControl createProjectileControl(Spatial emittingEntity,
                                                               Vector3f targetLocation) {
        Vector3f gravity = this.gameContainer.physics().getGravity();
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
