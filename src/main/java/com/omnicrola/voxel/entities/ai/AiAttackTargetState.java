package com.omnicrola.voxel.entities.ai;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.old.MotionGovernorControl;
import com.omnicrola.voxel.entities.control.old.WeaponsController;
import com.omnicrola.voxel.util.VoxelUtil;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiAttackTargetState implements IAiState {
    private final WeaponsController weaponsController;
    private final MotionGovernorControl motionGovernor;
    private Spatial currentTarget;

    public AiAttackTargetState(WeaponsController weaponsController, MotionGovernorControl motionGovernor) {
        this.weaponsController = weaponsController;
        this.motionGovernor = motionGovernor;
    }

    public void setTarget(Spatial spatial) {
        this.currentTarget = spatial;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (targetIsAlive()) {

            boolean isNotInRange = !this.weaponsController.isInRangeOfTarget(currentTarget);
            if (isNotInRange) {
                Vector3f worldTranslation = currentTarget.getWorldTranslation();
                this.motionGovernor.moveToward(worldTranslation);
            } else {
                this.weaponsController.setTarget(currentTarget);
            }
        } else {
            this.currentTarget = null;
            entityAiController.setState(AiHoldPositionState.class);
        }
    }

    private boolean targetIsAlive() {
        return VoxelUtil.isAlive(this.currentTarget);
    }
}
