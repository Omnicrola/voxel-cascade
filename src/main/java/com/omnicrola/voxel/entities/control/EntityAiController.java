package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.omnicrola.voxel.entities.EntityData;

/**
 * Created by omnic on 1/17/2016.
 */
public class EntityAiController extends AbstractVoxelControl {

    private enum Goal {
        NONE,
        HOLD_POSITION,
        ATTACK_TARGET,
        MOVE_TO_POSITION, STOP;
    }

    private Vector3f currentTargetLocation;
    private Goal currentGoal;

    public EntityAiController() {
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
        setGoal(Goal.MOVE_TO_POSITION);
    }

    public void stop() {
        this.currentTargetLocation = null;
        setGoal(Goal.STOP);
    }

    private void setGoal(Goal goal) {
        this.currentGoal = goal;
    }

}
