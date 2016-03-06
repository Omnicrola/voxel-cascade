package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by omnic on 3/5/2016.
 */
public class Frontier {

    private class FrontierPrioritizer implements java.util.Comparator<VoxelData> {
        @Override
        public int compare(VoxelData v1, VoxelData v2) {
            float distance1 = v1.getLocation().distance(endLocation);
            float distance2 = v2.getLocation().distance(endLocation);
            if (distance1 > distance2) {
                return -1;
            } else if (distance2 < distance1) {
                return 1;
            }
            return 0;
        }
    }

    private final ITerrainManager terrainManager;
    private final Vector3f endLocation;
    private Queue<VoxelData> currentFrontier;
    private final ArrayList<VoxelData> alreadyProcessed;

    public Frontier(ITerrainManager terrainManager, Vector3f currentLocation, Vector3f endLocation) {
        this.terrainManager = terrainManager;
        this.endLocation = endLocation;
        VoxelData current = terrainManager.getVoxelAt(currentLocation);
        this.alreadyProcessed = new ArrayList<>();
        this.currentFrontier = new PriorityQueue<>(new FrontierPrioritizer());
        expandFrontier(current);
    }

    private void expandFrontier(VoxelData voxelData) {
        this.terrainManager.getNeighborsOf(voxelData)
                .stream()
                .filter(v -> isNotProcessed(v))
                .forEach(v -> {
                    this.alreadyProcessed.add(v);
                    this.currentFrontier.add(v);
                });
    }

    private boolean isNotProcessed(VoxelData v) {
        return !this.alreadyProcessed.contains(v);
    }

    public boolean hasNext() {
        return this.currentFrontier.size() > 0;
    }

    public VoxelData next() {
        return this.currentFrontier.poll();
    }
}
