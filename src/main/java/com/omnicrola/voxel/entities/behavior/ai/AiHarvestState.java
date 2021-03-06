package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;
import com.omnicrola.voxel.entities.control.resources.IHarvestTarget;
import com.omnicrola.voxel.entities.control.resources.ResourceHarvestController;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiHarvestState implements IAiState {

    private ResourceHarvestController harvestController;
    private EntityMotionControl motionGovernor;

    public AiHarvestState(ResourceHarvestController harvestController, EntityMotionControl motionGovernor) {
        this.harvestController = harvestController;
        this.motionGovernor = motionGovernor;
    }

    public void setTarget(IHarvestTarget harvestTarget){
        this.harvestController.setTarget(harvestTarget);
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (this.harvestController.isHarvesting()) {
            if (this.harvestController.isInRange()) {
                this.motionGovernor.holdPosition();
            } else {
                Vector3f location = this.harvestController.getTargetLocation();
                this.motionGovernor.moveToward(location);
            }
        } else {
            entityAiController.setState(AiHoldPositionState.class);
        }

    }
}
