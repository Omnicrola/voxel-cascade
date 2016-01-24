package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class WeaponsController extends AbstractControl {

    private static final float SIXTY_SECONDS = 60f;

    private float timeSinceLastShot = 0f;
    private Spatial currentTarget;
    private IGameWorld gameWorld;
    private WeaponDefinition weaponDefinition;
    private Vector3f projectileOffset;
    private int projectileId;

    public WeaponsController(IGameWorld gameWorld,
                             WeaponDefinition weaponDefinition,
                             Vector3f projectileOffset,
                             int projectileId) {
        this.gameWorld = gameWorld;
        this.weaponDefinition = weaponDefinition;
        this.projectileOffset = projectileOffset;
        this.projectileId = projectileId;
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
        Float hitpoints = this.spatial.getUserData(EntityDataKeys.HITPOINTS);
        boolean alive = hitpoints != null && hitpoints.floatValue() >= 0f;
        return alive;
    }

    private boolean weaponHasCooledDown() {
        float timeBetweenShots = SIXTY_SECONDS / this.weaponDefinition.getRoundsPerMinute();
        return this.timeSinceLastShot >= timeBetweenShots;
    }

    private void fireWeapon() {
        this.timeSinceLastShot = 0;

        Vector3f targetLocation = this.currentTarget.getWorldTranslation();
        Vector3f ourLocation = this.spatial.getWorldTranslation().add(projectileOffset);
        Vector3f attackVector = targetLocation.subtract(ourLocation);

        Spatial projectile = this.gameWorld.build().projectile(this.spatial, this.projectileId, attackVector);
        projectile.setLocalTranslation(ourLocation);
        this.gameWorld.attach(projectile);
    }

    @Override
    public void controlRender(RenderManager rm, ViewPort vp) {

    }

    public float getRange() {
        return this.weaponDefinition.getRange();
    }

    public void clearTarget() {
        this.currentTarget = null;
    }

    public void setTarget(Spatial target) {
        this.currentTarget = target;
    }
}
