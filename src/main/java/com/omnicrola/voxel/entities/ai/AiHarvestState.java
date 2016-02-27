package com.omnicrola.voxel.entities.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.control.old.MotionGovernorControl;
import com.omnicrola.voxel.entities.resources.IHarvestTarget;
import com.omnicrola.voxel.entities.resources.ResourceHarvestController;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiHarvestState implements IAiState {

    private ResourceHarvestController harvestController;
    private MotionGovernorControl motionGovernor;

    public AiHarvestState(ResourceHarvestController harvestController, MotionGovernorControl motionGovernor) {
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
