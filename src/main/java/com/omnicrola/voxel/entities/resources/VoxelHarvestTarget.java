package com.omnicrola.voxel.entities.resources;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.VoxelData;

/**
 * Created by Eric on 2/11/2016.
 */
public class VoxelHarvestTarget implements IHarvestTarget{

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
    public float removeResources() {
        short amountRemoved = 1;
        short resources = this.voxelData.getResources();
        this.voxelData.setResources((short) (resources - amountRemoved));
        return amountRemoved;
    }
}
