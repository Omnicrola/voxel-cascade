package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.resources.IHarvestTarget;
import com.omnicrola.voxel.entities.resources.ResourceHarvestController;
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
        STOP,
        HARVEST, BUILD;
    }

    private Vector3f currentTargetLocation;
    private Goal currentGoal;
    private Spatial currentTarget;
    private WeaponsController weaponsController;
    private TargetingController targetingController;
    private ResourceHarvestController harvestController;
    private final BuildController buildController;
    private MotionGovernorControl motionGovernor;

    public EntityAiController(MotionGovernorControl motionGovernor,
                              WeaponsController weaponsController,
                              TargetingController targetingController,
                              ResourceHarvestController resourceHarvester,
                              BuildController buildController) {
        this.motionGovernor = motionGovernor;
        this.weaponsController = weaponsController;
        this.targetingController = targetingController;
        this.harvestController = resourceHarvester;
        this.buildController = buildController;
        setGoal(Goal.STOP);
    }

    public void orderMoveTo(Vector3f location) {
        this.currentTargetLocation = location;
        clearTarget();
        setGoal(Goal.MOVE_TO_POSITION);
    }

    public void orderStop() {
        this.currentTargetLocation = null;
        clearTarget();
        setGoal(Goal.STOP);
    }

    public void orderAttackLocation(Vector3f location) {
        setGoal(Goal.ATTACK_LOCATION);
        this.currentTargetLocation = location;
        clearTarget();
    }

    public void orderAttackTarget(Geometry targetEntity) {
        setGoal(Goal.ATTACK_TARGET);
        this.currentTarget = targetEntity;
        this.weaponsController.setTarget(targetEntity);
    }

    public void orderHarvest(IHarvestTarget harvestTarget) {
        this.harvestController.setTarget(harvestTarget);
        setGoal(Goal.HARVEST);
    }

    public void orderBuild(IConstructionPackage constructionPackage) {
        this.buildController.setPackage(constructionPackage);
        setGoal(Goal.BUILD);
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
            case HARVEST:
                updateHarvest(tpf);
                break;
            case BUILD:
                updateBuild();
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

    private void updateHarvest(float tpf) {
        if (this.harvestController.isHarvesting()) {
            if (this.harvestController.isInRange()) {
                this.motionGovernor.holdPosition();
            } else {
                Vector3f location = this.harvestController.getTargetLocation();
                this.motionGovernor.moveToward(location);
            }
        } else {
            setGoal(Goal.HOLD_POSITION);
        }
    }

    private void updateBuild() {
        if (this.buildController.isFinished()) {
            setGoal(Goal.HOLD_POSITION);
        } else {
            if (this.buildController.isInRange()) {
                this.motionGovernor.holdPosition();
            } else {
                this.motionGovernor.moveToward(this.buildController.getTargetLocation());
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    private void clearTarget() {
        this.currentTarget = null;
        this.weaponsController.clearTarget();
    }

    private void setGoal(Goal goal) {
        this.currentGoal = goal;
    }
}
