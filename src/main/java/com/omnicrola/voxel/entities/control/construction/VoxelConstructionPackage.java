package com.omnicrola.voxel.entities.control.construction;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.entities.commands.IConstructionPackage;
import com.omnicrola.voxel.entities.control.resources.VoxelQueue;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.IVoxelType;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 3/4/2016.
 */
public class VoxelConstructionPackage implements IConstructionPackage {
    private static final float BASE_BUILD_RATE = 1.0f;

    private final ITerrainManager terrainManager;
    private VoxelQueue constructionQueue;
    private VoxelData currentTargetVoxel;
    private Vector3f currentTargetLocation;
    private final IVoxelType voxelType;

    public VoxelConstructionPackage(ITerrainManager terrainManager,
                                    byte voxelType,
                                    VoxelQueue constructionQueue) {
        this.terrainManager = terrainManager;
        this.constructionQueue = constructionQueue;
        this.voxelType = this.terrainManager.getVoxelType(voxelType);
        advanceQueue();
    }

    @Override
    public Vector3f getLocation() {
        return this.currentTargetLocation.clone();
    }

    @Override
    public boolean isFinished() {
        boolean queueIsEmpty = this.constructionQueue.isEmpty();
        boolean noCurrentTarget = this.currentTargetVoxel == null;
        return queueIsEmpty && noCurrentTarget;
    }

    @Override
    public float applyResourceTic(float tpf) {
        float amountUsed = getAmountUsed(tpf);
        updateBuildQueue();
        return amountUsed;
    }

    private void updateBuildQueue() {
        float requiredResources = this.voxelType.resourcesRequired();
        float resourcesUsed = getResourcesInCurrentVoxel();
        if (resourcesUsed >= requiredResources) {
            this.currentTargetVoxel.setType(this.voxelType);
            advanceQueue();
        }
    }

    private void advanceQueue() {
        this.currentTargetVoxel = this.constructionQueue.pop();
        if (this.currentTargetVoxel != null) {
            this.currentTargetLocation = this.currentTargetVoxel.getLocation();
        }
        System.out.println("construction done, next target: " + this.currentTargetVoxel);
    }

    private float getAmountUsed(float tpf) {
        if (this.currentTargetVoxel == null) {
            return 0;
        }
        float totalRequired = this.voxelType.resourcesRequired();
        float resourcesNeeded = totalRequired - getResourcesInCurrentVoxel();
        float resourcesUsed = BASE_BUILD_RATE * tpf;
        if (resourcesNeeded >= resourcesUsed) {
            return resourcesUsed;
        } else {
            return resourcesNeeded;
        }

    }

    private float getResourcesInCurrentVoxel() {
        if (this.currentTargetVoxel == null) {
            return 0;
        }
        return this.currentTargetVoxel.getResources();
    }

    @Override
    public void completeConstruction() {

    }
}
