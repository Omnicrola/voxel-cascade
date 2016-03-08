package com.omnicrola.voxel.entities.behavior.ai.pathing;

/**
 * Created by omnic on 3/5/2016.
 */
public class NavigationPath {

    private VoxelAstarPathFinder.PathNode startNode;
    private VoxelAstarPathFinder.PathNode nextNode;

    public NavigationPath(VoxelAstarPathFinder.PathNode startNode) {
        this.startNode = startNode;
        this.nextNode = startNode;
    }

    public boolean hasNext() {
        return this.nextNode.nextNode != null;
    }

    public VoxelAstarPathFinder.PathNode next() {
        VoxelAstarPathFinder.PathNode currentNode = this.nextNode;
        this.nextNode = this.nextNode.nextNode;
        return currentNode;
    }
}
