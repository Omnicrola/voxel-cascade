package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 2/11/2016.
 */
public class VoxelHarvestTarget implements IHarvestTarget {

    public static final float BASE_HARVEST_RATE = 1f;
    private VoxelData voxelData;
    private Vector3f targetLocation;

    public VoxelHarvestTarget(VoxelData voxelData, Vector3f targetLocation) {
        this.voxelData = voxelData;
        this.targetLocation = targetLocation;
    }

    public Vector3f getLocation() {
        return this.targetLocation.clone();
    }

    @Override
    public boolean hasResources() {
        return this.voxelData.getResources() > 0;
    }

    @Override
    public float removeResources(float tpf) {
        float amountRemoved = BASE_HARVEST_RATE * tpf;
        float resources = this.voxelData.getResources();
        this.voxelData.setResources(resources - amountRemoved);
        return amountRemoved;
    }

    @Override
    public void remove() {
        this.voxelData.removeVoxel();
    }
}
