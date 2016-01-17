package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractControl {


    private enum Goal {
        NONE,
        HOLD_POSITION,
        ATTACK_TARGET,
        ATTACK_LOCATION,
        MOVE_TO_POSITION,
        STOP;
    }

    private Vector3f currentTargetLocation;
    private Goal currentGoal;
    private Geometry currentTarget;
    private WeaponsController weaponsController;
    private MotionGovernorControl motionGovernor;

    public EntityAiController(MotionGovernorControl motionGovernor, WeaponsController weaponsController) {
        this.motionGovernor = motionGovernor;
        this.weaponsController = weaponsController;
        setGoal(Goal.NONE);
    }

    @Override
    protected void controlUpdate(float tpf) {
        switch (this.currentGoal) {
            case STOP:
                updateStop();
                break;
            case HOLD_POSITION:
                updateHoldPosition();
                break;
            case ATTACK_TARGET:
                updateAttackTarget();
                break;
            case ATTACK_LOCATION:
                updateAttackLocation();
                break;
            case MOVE_TO_POSITION:
                updateMoveToLocation();
                break;
            case NONE:
            default:
        }
    }

    private void updateStop() {
        this.currentTargetLocation = this.spatial.getWorldTranslation();
        setGoal(Goal.HOLD_POSITION);
    }

    private void updateMoveToLocation() {
        boolean hasArrived = this.motionGovernor.seekAndArriveAtRange(this.currentTargetLocation, 3, 0);
        if (hasArrived) {
            this.currentTargetLocation = this.spatial.getWorldTranslation();
            setGoal(Goal.HOLD_POSITION);
        }
    }

    private void updateAttackTarget() {
        if (!this.weaponsController.isInRangeOfTarget(this.currentTarget)) {
            Vector3f worldTranslation = this.currentTarget.getWorldTranslation();
            float weaponRange = this.weaponsController.getRange();
            this.motionGovernor.seekAndArriveAtRange(worldTranslation, 0, weaponRange * 0.9f);
        }
    }

    private void updateAttackLocation() {
        updateMoveToLocation();
    }

    private void updateHoldPosition() {
        Vector3f location = this.spatial.getWorldTranslation();
        float distanceFromTarget = this.currentTargetLocation.subtract(location).length();
        if (distanceFromTarget > 0.5f) {
            this.motionGovernor.seekAndArriveAtRange(this.currentTargetLocation, 1.0f, 0);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public void moveToLocation(Vector3f location) {
        this.currentTargetLocation = location;
        clearTarget();
        setGoal(Goal.MOVE_TO_POSITION);
    }

    public void stop() {
        this.currentTargetLocation = null;
        clearTarget();
        setGoal(Goal.STOP);
    }

    public void attackLocation(Vector3f location) {
        setGoal(Goal.ATTACK_LOCATION);
        this.currentTargetLocation = location;
        clearTarget();
    }

    public void attackEntity(Geometry targetEntity) {
        setGoal(Goal.ATTACK_TARGET);
        this.currentTarget = targetEntity;
        this.weaponsController.setTarget(targetEntity);
    }

    private void clearTarget() {
        this.currentTarget = null;
        this.weaponsController.clearTarget();
    }

    private void setGoal(Goal goal) {
        this.currentGoal = goal;
    }
}
