package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 2/11/2016.
 */
public class VoxelHarvestTarget implements IHarvestTarget {

    public static final float BASE_HARVEST_RATE = 1f;
    private VoxelData currentTargetVoxel;
    private Vector3f currentTargetLocation;
    private VoxelQueue voxelQueue;

    public VoxelHarvestTarget(VoxelQueue voxelQueue) {
        this.voxelQueue = voxelQueue;
        removeResources(0f);
    }

    @Override
    public Vector3f getLocation() {
        return this.currentTargetLocation.clone();
    }

    @Override
    public boolean hasResources() {
        boolean currentTargetHasResources = this.currentTargetVoxel.getResources() > 0;
        boolean queueIsNotEmpty = this.voxelQueue.isNotEmpty();
        return currentTargetHasResources || queueIsNotEmpty;
    }

    @Override
    public float removeResources(float tpf) {
        float amountRemoved = getAmountRemoved(tpf);
        updateHarvestQueue();
        return amountRemoved;
    }

    private void updateHarvestQueue() {
        float resources = 0;
        if (this.currentTargetVoxel != null) {
            resources = this.currentTargetVoxel.getResources();
        }
        if (resources <= 0 && this.voxelQueue.isNotEmpty()) {
            if (this.currentTargetVoxel != null) {
                makeCurrentTargetEmpty();
            }
            this.currentTargetVoxel = this.voxelQueue.pop();
            this.currentTargetLocation = this.currentTargetVoxel.getLocation();
        }
    }

    private void makeCurrentTargetEmpty() {
        this.currentTargetVoxel.removeVoxel();
    }

    private float getAmountRemoved(float tpf) {
        if (this.currentTargetVoxel == null) {
            return 0;
        }
        float amountRemoved = BASE_HARVEST_RATE * tpf;
        float currentResourceAmount = this.currentTargetVoxel.getResources();
        if (currentResourceAmount <= amountRemoved) {
            this.currentTargetVoxel.setResources(0);
            return currentResourceAmount;
        } else {
            this.currentTargetVoxel.setResources(currentResourceAmount - amountRemoved);
            return amountRemoved;
        }
    }

    @Override
    public void remove() {
        makeCurrentTargetEmpty();
    }
}
