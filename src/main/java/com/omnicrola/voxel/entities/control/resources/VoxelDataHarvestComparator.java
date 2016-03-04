package com.omnicrola.voxel.entities.control.resources;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Comparator;

/**
 * Created by Eric on 3/3/2016.
 */
public class VoxelDataHarvestComparator implements Comparator<VoxelData> {
    private Vector3f targetLocation;

    public VoxelDataHarvestComparator(Vector3f targetLocation) {
        this.targetLocation = targetLocation;
    }

    @Override
    public int compare(VoxelData o1, VoxelData o2) {
        float d1 = o1.getLocation().distance(this.targetLocation);
        float d2 = o2.getLocation().distance(this.targetLocation);
        if (d1 > d2) {
            return 1;
        } else if (d1 < d2) {
            return -1;
        }
        return 0;
    }
}
