package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiMoveToLocationState implements IAiState {

    public static final float MINIMUM_DISTANCE = 0.5f;
    private EntityMotionControl motionGovernor;
    private Vector3f targetLocation;

    public AiMoveToLocationState(EntityMotionControl motionGovernor) {
        this.motionGovernor = motionGovernor;
    }

    public void setTarget(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (hasArrived(entityAiController)) {
            entityAiController.setState(AiStopState.class);
        } else {
            this.motionGovernor.moveToward(this.targetLocation);
        }

    }

    private boolean hasArrived(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        return currentLocation.distance(this.targetLocation) <= MINIMUM_DISTANCE;
    }


}
