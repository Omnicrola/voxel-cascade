package com.omnicrola.voxel.entities.ai;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiStopState implements IAiState {

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        entityAiController.setState(AiHoldPositionState.class);
    }
}
