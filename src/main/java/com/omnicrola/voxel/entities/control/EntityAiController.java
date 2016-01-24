package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.settings.EntityDataKeys;

import java.util.Optional;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractControl {

    private enum Goal {
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
    private TargetingController targetingController;
    private MotionGovernorControl motionGovernor;

    public EntityAiController(MotionGovernorControl motionGovernor, WeaponsController weaponsController, TargetingController targetingController) {
        this.motionGovernor = motionGovernor;
        this.weaponsController = weaponsController;
        this.targetingController = targetingController;
        setGoal(Goal.STOP);
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
            default:
        }
    }

    private void updateStop() {
        this.currentTargetLocation = this.spatial.getWorldTranslation();
        setGoal(Goal.HOLD_POSITION);
    }

    private void updateMoveToLocation() {
        boolean hasArrived = this.spatial.getWorldTranslation().distance(this.currentTargetLocation) <= 0.5f;
        if (hasArrived) {
            System.out.println("arrived");
            this.currentTargetLocation = this.spatial.getWorldTranslation();
            setGoal(Goal.HOLD_POSITION);
        } else {
            this.motionGovernor.moveToward(this.currentTargetLocation);
        }
    }

    private void updateAttackTarget() {
        if (targetIsAlive()) {

            boolean isNotInRange = !this.weaponsController.isInRangeOfTarget(this.currentTarget);
            if (isNotInRange) {
                Vector3f worldTranslation = this.currentTarget.getWorldTranslation();
                this.motionGovernor.moveToward(worldTranslation);
            } else {
                updateHoldPosition();
            }
        } else {
            this.currentTarget = null;
            setGoal(Goal.HOLD_POSITION);
        }
    }

    private boolean targetIsAlive() {
        Float hitpoints = this.currentTarget.getUserData(EntityDataKeys.HITPOINTS);
        return hitpoints != null && hitpoints.floatValue() > 0;
    }

    private void updateAttackLocation() {
        updateMoveToLocation();
    }

    private void updateHoldPosition() {
        Optional<Spatial> closestTarget = this.targetingController.getClosestTarget();
        if (closestTarget.isPresent()) {
            this.weaponsController.setTarget(closestTarget.get());
        }
        this.motionGovernor.holdPosition();
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
        System.out.println("new goal: " + goal);
        this.currentGoal = goal;
    }
}
