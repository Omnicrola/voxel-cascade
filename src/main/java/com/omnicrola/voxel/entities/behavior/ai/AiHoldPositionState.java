package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;
import com.omnicrola.voxel.entities.control.weapon.TargetingController;
import com.omnicrola.voxel.entities.control.weapon.WeaponsController;

import java.util.Optional;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiHoldPositionState implements IAiState {
    private TargetingController targetingController;
    private WeaponsController weaponsController;
    private EntityMotionControl motionGovernor;

    public AiHoldPositionState(TargetingController targetingController,
                               WeaponsController weaponsController,
                               EntityMotionControl motionGovernor) {
        this.targetingController = targetingController;
        this.weaponsController = weaponsController;
        this.motionGovernor = motionGovernor;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        Optional<Spatial> closestTarget = this.targetingController.getClosestTarget();
        if (closestTarget.isPresent()) {
            this.weaponsController.setTarget(closestTarget.get());
        }
        this.motionGovernor.holdPosition();

    }
}
