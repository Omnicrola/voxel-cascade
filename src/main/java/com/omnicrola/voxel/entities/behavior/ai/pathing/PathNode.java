package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 3/10/2016.
 */
public class PathNode {

    VoxelData voxel;
    boolean hasBeenProcessed;
    boolean isOnFrontier;
    float cost = Float.MAX_VALUE;
    PathNode cameFrom;
    public float fScore;
    public boolean isViable;
    public float priority;

    public PathNode(VoxelData voxel) {
        this.voxel = voxel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PathNode)) return false;

        PathNode pathNode = (PathNode) o;

        return pathNode.voxel.getGridLocation().equals(this.voxel.getGridLocation());

    }

    @Override
    public int hashCode() {
        return voxel != null ? voxel.getGridLocation().hashCode() : 0;
    }

    public float x() {
        return this.voxel.getGridLocation().getX();
    }

    public float y() {
        return this.voxel.getGridLocation().getY();
    }

    public float z() {
        return this.voxel.getGridLocation().getZ();
    }

    public float distance(PathNode node) {
        Vec3i otherLocation = node.voxel.getGridLocation();
        Vec3i ourLocation = this.voxel.getGridLocation();
        return ourLocation.distance(otherLocation);
    }
}
