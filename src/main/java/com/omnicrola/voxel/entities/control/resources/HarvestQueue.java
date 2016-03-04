package com.omnicrola.voxel.entities.control.resources;

import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Eric on 3/3/2016.
 */
public class HarvestQueue {

    private final Queue<VoxelData> queue;
    private final VoxelDataHarvestComparator voxelDataHarvestComparator;

    public HarvestQueue(Collection<VoxelData> selectedVoxels, VoxelDataHarvestComparator voxelDataHarvestComparator) {
        this.voxelDataHarvestComparator = voxelDataHarvestComparator;
        this.queue = new PriorityQueue<>(voxelDataHarvestComparator);
        this.queue.addAll(selectedVoxels);
    }

    public VoxelData pop() {
        return this.queue.poll();
    }

    public boolean isNotEmpty() {
        return this.queue.size() > 0;
    }
}
