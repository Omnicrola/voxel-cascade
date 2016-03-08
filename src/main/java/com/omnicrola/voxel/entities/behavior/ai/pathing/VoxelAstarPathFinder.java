package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omnic on 3/5/2016.
 */
public class VoxelAstarPathFinder {

    private ITerrainManager terrainManager;

    public VoxelAstarPathFinder(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    public PathNode findPath(Vector3f startLocation, Vector3f targetLocation) {
        HashMap<Vec3i, PathNode> examinedNodes = new HashMap<>();
        List<PathNode> frontier = new ArrayList<>();
        PathNode currentNode = new PathNode(this.terrainManager.getVoxelAt(startLocation));
        currentNode.cost = 0;
        PathNode targetNode = new PathNode(this.terrainManager.getVoxelAt(targetLocation));

        frontier.add(currentNode);
        while (frontier.size() > 0) {
            currentNode = frontier.remove(0);
            currentNode.onClosedList = true;

            List<VoxelData> neighborVoxels = this.terrainManager.getNeighborsOf(currentNode.voxel);
            for (VoxelData neighbor : neighborVoxels) {
                if (terrainManager.isEmptyWithFloor(neighbor.getGridLocation())) {

                    PathNode possibleNextNode;
                    if (isNewNode(neighbor, examinedNodes)) {
                        possibleNextNode = new PathNode(neighbor);
                        examinedNodes.put(neighbor.getGridLocation(), possibleNextNode);
                    } else {
                        possibleNextNode = examinedNodes.get(neighbor.getGridLocation());
                    }
                    if (!possibleNextNode.onClosedList) {
                        float cost = getCost(currentNode, possibleNextNode);
                        if (cost < possibleNextNode.cost) {
                            possibleNextNode.cost = cost;
                            possibleNextNode.nextNode = currentNode;
                        }
                        if (!possibleNextNode.onOpenList) {
                            Vec3i pathLocation = possibleNextNode.voxel.getGridLocation();
                            Vec3i targetNodeLocation = targetNode.voxel.getGridLocation();
                            if (possibleNextNode.equals(targetNode)) {
                                possibleNextNode.nextNode = currentNode;
                                return possibleNextNode;
                            } else {
                            }
                            possibleNextNode.onOpenList = true;
                            frontier.add(possibleNextNode);
                        }
                    }

                }
            }
            frontier.sort(new DistanceToTargetComparator(targetLocation));
        }

        return null;
    }

    private float getCost(PathNode previousNode, PathNode nextNode) {
        float cost = 0;
        Vec3i g1 = previousNode.voxel.getGridLocation();
        Vec3i g2 = nextNode.voxel.getGridLocation();
        cost += compare(g1.getX(), g2.getX());
        cost += compare(g1.getY(), g2.getY())*100;
        cost += compare(g1.getZ(), g2.getZ());
        cost += previousNode.cost;
        cost += distanceSquared(previousNode, nextNode);
        return cost;
    }

    private float distanceSquared(PathNode n1, PathNode n2) {
        Vec3i g1 = n1.voxel.getGridLocation();
        Vec3i g2 = n2.voxel.getGridLocation();
        float a = g1.getX() - g2.getX();
        float b = g1.getY() - g2.getY();
        float c = g1.getZ() - g2.getZ();
        float distanceSq = a * a + b * b + c * c;
        return distanceSq;
    }

    private float compare(int x1, int x2) {
        return (x1 == x2) ? 0 : 1;
    }

    private boolean isNewNode(VoxelData voxelData, HashMap<Vec3i, PathNode> examinedNodes) {
        for (PathNode node : examinedNodes.values()) {
            if (voxelData.getGridLocation().equals(node.voxel.getGridLocation())) {
                return false;
            }
        }
        return true;
    }

    public static class PathNode {

        public VoxelData voxel;
        public boolean onClosedList;
        public boolean onOpenList;
        public float cost = Float.MAX_VALUE;
        public PathNode nextNode;

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
    }

    private class DistanceToTargetComparator implements Comparator<PathNode> {
        private final Vector3f tempLocation;
        private Vector3f targetLocation;

        public DistanceToTargetComparator(Vector3f targetLocation) {
            this.targetLocation = targetLocation;
            this.tempLocation = new Vector3f();
        }

        @Override
        public int compare(PathNode n1, PathNode n2) {
            float distance1 = set(n1).distance(this.targetLocation);
            float distance2 = set(n2).distance(this.targetLocation);
            return Float.compare(distance1, distance2);
        }

        private Vector3f set(PathNode node) {
            Vec3i l = node.voxel.getGridLocation();
            return this.tempLocation.set(l.getX(), l.getY(), l.getZ());
        }
    }
}
