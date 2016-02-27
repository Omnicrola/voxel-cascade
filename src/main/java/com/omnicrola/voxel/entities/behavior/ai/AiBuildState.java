package com.omnicrola.voxel.entities.behavior.ai;

import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.control.construction.BuildController;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiBuildState implements IAiState {
    EntityMotionControl motionGovernor;
    BuildController buildController;

    public AiBuildState(EntityMotionControl motionGovernor, BuildController buildController) {
        this.motionGovernor = motionGovernor;
        this.buildController = buildController;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (this.buildController.isFinished()) {
            entityAiController.setState(AiHoldPositionState.class);
        } else {
            if (this.buildController.isInRange()) {
                this.motionGovernor.holdPosition();
            } else {
                this.motionGovernor.moveToward(this.buildController.getTargetLocation());
            }
        }
    }

    public void setPackage(IConstructionPackage constructionPackage) {
        this.buildController.setPackage(constructionPackage);
    }
}
