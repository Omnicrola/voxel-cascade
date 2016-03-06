package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.behavior.ai.pathing.NavigationPath;
import com.omnicrola.voxel.entities.behavior.ai.pathing.VoxelAstarPathFinder;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiMoveToLocationState implements IAiState {

    public static final float MINIMUM_DISTANCE = 0.5f;
    private EntityMotionControl motionGovernor;
    private VoxelAstarPathFinder pathFinder;
    private Vector3f targetLocation;

    public AiMoveToLocationState(EntityMotionControl motionGovernor, VoxelAstarPathFinder pathFinder) {
        this.motionGovernor = motionGovernor;
        this.pathFinder = pathFinder;
    }

    public void setTarget(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (hasArrived(entityAiController)) {
            entityAiController.setState(AiStopState.class);
        } else {
            Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
            NavigationPath path = this.pathFinder.findPath(currentLocation, this.targetLocation);
            Vector3f nextPathNode = path.next();
            this.motionGovernor.moveToward(nextPathNode);
        }

    }

    private boolean hasArrived(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        return currentLocation.distance(this.targetLocation) <= MINIMUM_DISTANCE;
    }


}
