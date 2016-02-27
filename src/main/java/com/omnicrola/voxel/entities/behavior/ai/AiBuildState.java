package com.omnicrola.voxel.entities.behavior.ai;

import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.control.build.BuildController;
import com.omnicrola.voxel.entities.control.old.MotionGovernorControl;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiBuildState implements IAiState {
    MotionGovernorControl motionGovernor;
    BuildController buildController;

    public AiBuildState(MotionGovernorControl motionGovernor, BuildController buildController) {
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
