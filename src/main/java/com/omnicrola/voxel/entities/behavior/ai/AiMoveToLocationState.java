package com.omnicrola.voxel.entities.behavior.ai;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.behavior.ai.pathing.VoxelAstarPathFinder;
import com.omnicrola.voxel.entities.control.move.EntityMotionControl;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelType;

/**
 * Created by Eric on 2/17/2016.
 */
public class AiMoveToLocationState implements IAiState {

    public static final float MINIMUM_DISTANCE = 0.5f;
    private EntityMotionControl motionGovernor;
    private VoxelAstarPathFinder pathFinder;
    private Vector3f targetLocation;
    private VoxelAstarPathFinder.PathNode navigationPath;
    private VoxelAstarPathFinder.PathNode startOfPath;
    private ITerrainManager terrainManager;

    public AiMoveToLocationState(EntityMotionControl motionGovernor, VoxelAstarPathFinder pathFinder, ITerrainManager terrainManager) {
        this.motionGovernor = motionGovernor;
        this.pathFinder = pathFinder;
        this.terrainManager = terrainManager;
    }

    public void setTarget(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
        tracePath(this.startOfPath, VoxelType.GREY);
        this.navigationPath = null;
    }

    @Override
    public void update(EntityAiController entityAiController, float tpf) {
        if (hasArrived(entityAiController)) {
            if (this.navigationPath.nextNode == null) {
                tracePath(this.startOfPath, VoxelType.GREY);
                this.startOfPath = null;
                this.motionGovernor.stop();
                entityAiController.setState(AiStopState.class);
            } else {
                this.navigationPath = this.navigationPath.nextNode;
            }
        } else {
            if (this.navigationPath == null) {
                calculateNewPath(entityAiController);
            }
            if (this.navigationPath != null) {
                Vector3f targetPosition = this.navigationPath.voxel.getGridLocation().asVector3f();
                System.out.println("move toward: " + targetPosition);
                this.motionGovernor.moveToward(targetPosition);
            }
        }

    }

    private void calculateNewPath(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        long startTime = System.nanoTime();
        VoxelAstarPathFinder.PathNode path = this.pathFinder.findPath(currentLocation, this.targetLocation);
        float elapsed = (System.nanoTime() - startTime) / 1_000_000f;
        System.out.println("Found path to : " + this.targetLocation + " from " + currentLocation);
        System.out.println("Pathfinding time: " + elapsed);
        this.navigationPath = path;
        this.startOfPath = path;

        tracePath(path, VoxelType.BLUE);
    }

    private void tracePath(VoxelAstarPathFinder.PathNode node, VoxelType voxelType) {
        if (node != null) {
            this.terrainManager.getVoxelAt(node.voxel.getGridLocation().translate(0, -1, 0)).setType(voxelType);
            tracePath(node.nextNode, voxelType);
        }
    }

    private boolean hasArrived(EntityAiController entityAiController) {
        Vector3f currentLocation = entityAiController.getSpatial().getWorldTranslation();
        return currentLocation.distance(this.targetLocation) <= MINIMUM_DISTANCE;
    }


}
