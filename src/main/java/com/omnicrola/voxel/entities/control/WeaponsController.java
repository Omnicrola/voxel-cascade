package com.omnicrola.voxel.entities.control;

import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.util.VectorUtil;
import com.omnicrola.voxel.engine.physics.CollisionController;
import com.omnicrola.voxel.engine.physics.ProjectileCollisionHandler;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.settings.EntityDataKeys;

/**
 * Created by omnic on 1/17/2016.
 */
public class WeaponsController extends AbstractControl {

    private float weaponCooldown = 1.0f;
    private float weaponRange = 3f;
    private float timeSinceLastShot = 0f;
    private Geometry currentTarget;
    private IGameContainer gameContainer;
    private Vector3f muzzleOffset = new Vector3f(0, 1, 0);

    public WeaponsController(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
    }

    public boolean isInRangeOfTarget(Geometry target) {
        Vector3f ourLocation = this.spatial.getWorldTranslation();
        Vector3f targetLocation = target.getWorldTranslation();
        float distanceToTarget = targetLocation.subtract(ourLocation).length();
        return distanceToTarget <= this.weaponRange;
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
        return this.timeSinceLastShot >= this.weaponCooldown;
    }

    private void fireWeapon() {
        this.timeSinceLastShot = 0;
        Vector3f targetLocation = this.currentTarget.getWorldTranslation();
        Vector3f ourLocation = this.spatial.getWorldTranslation();
        Vector3f attackVector = VectorUtil.scale(targetLocation.subtract(ourLocation).normalize(), 5f);

        Geometry projectile = this.gameContainer.world().build().sphere(0.0125f, ColorRGBA.White);
        projectile.setLocalTranslation(ourLocation);
        this.gameContainer.world().attach(projectile);

        projectile.setUserData(EntityDataKeys.IS_PROJECTILE, true);
        projectile.setUserData(EntityDataKeys.PROJECTILE_OWNER_SPATIAL, this.spatial);
        projectile.setUserData(EntityDataKeys.PROJECTILE_DAMAGE, 1.0f);

        LinearProjectileControl linearProjectileControl = new LinearProjectileControl(attackVector);
        projectile.addControl(linearProjectileControl);
        this.gameContainer.physics().addControl(linearProjectileControl);

        projectile.addControl(new CollisionController(new ProjectileCollisionHandler(projectile, this.gameContainer.physics())));
    }

    @Override
    public void controlRender(RenderManager rm, ViewPort vp) {

    }

    public float getRange() {
        return weaponRange;
    }

    public void clearTarget() {
        this.currentTarget = null;
    }

    public void setTarget(Geometry target) {
        this.currentTarget = target;
    }
}
