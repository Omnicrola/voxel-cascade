package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.util.VectorUtil;
import com.omnicrola.voxel.data.units.WeaponDefinition;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/17/2016.
 */
public class WeaponsController extends AbstractControl {

    private static final float SIXTY_SECONDS = 60f;
    private static final Vector3f WEAPON_MOUNTING_OFFSET = new Vector3f(0, 1, 0);

    private float timeSinceLastShot = 0f;
    private Geometry currentTarget;
    private IGameWorld gameWorld;
    private Vector3f muzzleOffset = new Vector3f(0, 1, 0);
    private WeaponDefinition weaponDefinition;
    private int projectileId;

    public WeaponsController(IGameWorld gameWorld,
                             WeaponDefinition weaponDefinition,
                             int projectileId) {
        this.gameWorld = gameWorld;
        this.weaponDefinition = weaponDefinition;
        this.projectileId = projectileId;
    }

    public boolean isInRangeOfTarget(Geometry target) {
        Vector3f ourLocation = this.spatial.getWorldTranslation();
        Vector3f targetLocation = target.getWorldTranslation();
        float distanceToTarget = targetLocation.subtract(ourLocation).length();
        return distanceToTarget <= this.weaponDefinition.getRange();
    }

    @Override
    public void controlUpdate(float tpf) {
        this.timeSinceLastShot += tpf;
        if (this.currentTarget != null) {
            if (isInRangeOfTarget(this.currentTarget)) {
                if (weaponHasCooledDown()) {
                    fireWeapon();
                }
            }
        }
    }

    private boolean weaponHasCooledDown() {
        float timeBetweenShots = SIXTY_SECONDS / this.weaponDefinition.getRoundsPerMinute();
        return this.timeSinceLastShot >= timeBetweenShots;
    }

    private void fireWeapon() {
        this.timeSinceLastShot = 0;

        Vector3f targetLocation = this.currentTarget.getWorldTranslation();
        Vector3f ourLocation = this.spatial.getWorldTranslation().add(WEAPON_MOUNTING_OFFSET);
        Vector3f attackVector = VectorUtil.scale(targetLocation.subtract(ourLocation).normalize(), 5f);

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

    public void setTarget(Geometry target) {
        this.currentTarget = target;
    }
}
