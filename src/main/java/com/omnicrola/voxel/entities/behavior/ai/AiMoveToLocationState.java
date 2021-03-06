package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.behavior.ai.pathing.NavigationPath;
import com.omnicrola.voxel.entities.behavior.ai.pathing.VoxelAstarPathFinder;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;
import com.omnicrola.voxel.terrain.ITerrainManager;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiMoveToLocationState implements IAiState {

    public static final float MINIMUM_DISTANCE = 0.5f;
    private EntityMotionControl motionGovernor;
    private VoxelAstarPathFinder pathFinder;
    private Vector3f targetLocation;
    private NavigationPath navigationPath;
    private ITerrainManager terrainManager;

    public AiMoveToLocationState(EntityMotionControl motionGovernor,
                                 VoxelAstarPathFinder pathFinder,
                                 ITerrainManager terrainManager) {
        this.motionGovernor = motionGovernor;
        this.pathFinder = pathFinder;
        this.terrainManager = terrainManager;
    }

    public void setTarget(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
        this.navigationPath = null;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (hasArrived(entityAiController)) {
            if (this.navigationPath.hasNext()) {
                this.targetLocation = this.navigationPath.next();
            } else {
                this.motionGovernor.stop();
                entityAiController.setState(AiStopState.class);
            }
        } else {
            if (this.navigationPath == null) {
                calculateNewPath(entityAiController);
            } else {
                this.motionGovernor.moveToward(this.targetLocation);
            }
        }
    }

    private void calculateNewPath(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        this.navigationPath = this.pathFinder.findPath(currentLocation, this.targetLocation);
        if (this.navigationPath != null && this.navigationPath.hasNext()) {
            this.targetLocation = this.navigationPath.next();
        }
    }

    private boolean hasArrived(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        return currentLocation.distance(this.targetLocation) <= MINIMUM_DISTANCE;
    }
}
