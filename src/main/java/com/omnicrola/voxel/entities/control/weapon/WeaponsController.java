package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.entities.Projectile;
import com.omnicrola.voxel.settings.EntityDataKeys;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 1/17/2016.
 */
public class WeaponsController extends AbstractControl {

    private static final float SIXTY_SECONDS = 60f;

    private float timeSinceLastShot = 0f;
    private Spatial currentTarget;
    private WeaponDefinition weaponDefinition;
    private Vector3f projectileOffset;
    private IProjectileStrategy projectileFactory;
    private WorldManager worldManager;

    public WeaponsController(WorldManager worldManager,
                             WeaponDefinition weaponDefinition,
                             Vector3f projectileOffset,
                             IProjectileStrategy projectileFactory) {
        this.worldManager = worldManager;
        this.weaponDefinition = weaponDefinition;
        this.projectileOffset = projectileOffset;
        this.projectileFactory = projectileFactory;
    }

    public boolean isInRangeOfTarget(Spatial target) {
        Vector3f ourLocation = this.spatial.getWorldTranslation();
        Vector3f targetLocation = target.getWorldTranslation();
        float distanceToTarget = targetLocation.subtract(ourLocation).length();
        return distanceToTarget <= this.weaponDefinition.getRange();
    }

    @Override
    public void controlUpdate(float tpf) {
        this.timeSinceLastShot += tpf;
        if (this.currentTarget != null) {
            if (targetIsAlive()) {
                if (isInRangeOfTarget(this.currentTarget)) {
                    if (weaponHasCooledDown()) {
                        fireWeapon();
                    }
                }
            } else {
                this.currentTarget = null;
            }
        }
    }

    private boolean targetIsAlive() {
        Float hitpoints = this.currentTarget.getUserData(EntityDataKeys.HITPOINTS);
        boolean alive = hitpoints != null && hitpoints.floatValue() > 0f;
        return alive;
    }

    private boolean weaponHasCooledDown() {
        float timeBetweenShots = SIXTY_SECONDS / this.weaponDefinition.getRoundsPerMinute();
        return this.timeSinceLastShot >= timeBetweenShots;
    }

    private void fireWeapon() {
        this.timeSinceLastShot = 0;

        Vector3f targetLocation = this.currentTarget.getWorldTranslation();
        Projectile projectile = this.projectileFactory.spawnProjectile(this.spatial, targetLocation);
        this.worldManager.addProjectile(projectile);
        Vector3f initialPosition = this.spatial
                .getWorldTranslation()
                .clone()
                .add(this.projectileOffset);
        projectile.setLocation(initialPosition);

    }

    @Override
    public void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void clearTarget() {
        this.currentTarget = null;
    }

    public void setTarget(Spatial target) {
        this.currentTarget = target;
    }
}
