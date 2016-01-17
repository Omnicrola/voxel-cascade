package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.omnicrola.voxel.entities.EntityData;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractVoxelControl {

    private WeaponsController weaponsController;

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

    public EntityAiController(WeaponsController weaponsController) {
        this.weaponsController = weaponsController;
        setGoal(Goal.NONE);
    }

    @Override
    protected void voxelUpdate(float tpf, EntityData entityData) {
        switch (this.currentGoal) {
            case STOP:
                updateStop(entityData);
                break;
            case HOLD_POSITION:
                updateHoldPosition(entityData);
                break;
            case ATTACK_TARGET:
                updateAttackTarget(entityData);
                break;
            case ATTACK_LOCATION:
                updateAttackLocation(entityData);
                break;
            case MOVE_TO_POSITION:
                updateMoveToLocation(entityData);
                break;
            case NONE:
            default:
        }
    }

    private void updateStop(EntityData entityData) {
        this.currentTargetLocation = entityData.getLocation();
        setGoal(Goal.HOLD_POSITION);
    }

    private void updateMoveToLocation(EntityData entityData) {
        MotionGovernorControl motionGovernor = entityData.getMotionGovernor();
        boolean hasArrived = motionGovernor.seekAndArriveAtRange(this.currentTargetLocation, 3, 0);
        if (hasArrived) {
            this.currentTargetLocation = entityData.getLocation();
            setGoal(Goal.HOLD_POSITION);
        }
    }

    private void updateAttackTarget(EntityData entityData) {
        if (!this.weaponsController.isInRangeOfTarget(this.currentTarget)) {
            Vector3f worldTranslation = this.currentTarget.getWorldTranslation();
            float weaponRange = this.weaponsController.getRange();
            entityData.getMotionGovernor().seekAndArriveAtRange(worldTranslation, 0, weaponRange * 0.9f);
        }
    }

    private void updateAttackLocation(EntityData entityData) {
        updateMoveToLocation(entityData);
    }

    private void updateHoldPosition(EntityData entityData) {
        Vector3f location = entityData.getLocation();
        float distanceFromTarget = this.currentTargetLocation.subtract(location).length();
        if (distanceFromTarget > 0.5f) {
            entityData.getMotionGovernor().seekAndArriveAtRange(this.currentTargetLocation, 1.0f, 0);
        }
    }

    @Override
    protected void voxelRender(RenderManager rm, ViewPort vp) {
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
