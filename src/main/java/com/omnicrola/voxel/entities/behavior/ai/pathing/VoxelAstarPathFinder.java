package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.ITerrainManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 3/5/2016.
 */
public class VoxelAstarPathFinder {

    private static final float D = 1.0f;
    private final TerrainPathNodeAdapter terrainNodeAdapter;
//    private ITerrainManager terrainManager;


    public VoxelAstarPathFinder(ITerrainManager terrainManager) {
//        this.terrainManager = terrainManager;
        this.terrainNodeAdapter = new TerrainPathNodeAdapter(terrainManager);
    }

    public List<Vector3f> getAllNodesUsed() {
        return this.terrainNodeAdapter.getAll()
                .stream()
                .map(n -> n.voxel.getGridLocation().asVector3f())
                .collect(Collectors.toList());
    }

    public NavigationPath findPath(Vector3f startLocation, Vector3f targetLocation) {
        ArrayList<PathNode> frontier = new ArrayList<>();
        this.terrainNodeAdapter.reset();

        PathNode startNode = this.terrainNodeAdapter.getNodeAt(startLocation);
        startNode.cost = 0;
        startNode.isOnFrontier = true;
        frontier.add(startNode);
        PathNode goalNode = this.terrainNodeAdapter.getNodeAt(targetLocation);

        long start = System.nanoTime();
        int count = 0;
        while (frontier.size() > 0) {
            float elapsed = (System.nanoTime() - start) / 1_000_000f;
            if (elapsed > 1) {
                System.out.println("Exceeded 5ms pathfinding time. Nodes: " + count);
                return null;
            }
            Collections.sort(frontier, (n1, n2) -> Float.compare(n1.priority, n2.priority));
            PathNode currentNode = frontier.remove(0);
            count++;
            currentNode.hasBeenProcessed = true;
            List<PathNode> neighbors = this.terrainNodeAdapter.getNeighbors(currentNode);
            for (PathNode neighbor : neighbors) {
                if (neighbor.hasBeenProcessed) {
                    continue;
                }
                float cost = currentNode.cost + movementCost(neighbor, currentNode);
                if (cost < neighbor.cost) {
                    neighbor.cost = cost;
                    neighbor.priority = cost + heuristic(goalNode, neighbor);
                    neighbor.cameFrom = currentNode;
                }
                if (!neighbor.isOnFrontier) {
                    if (neighbor.equals(goalNode)) {
                        neighbor.cameFrom = currentNode;
                        return new NavigationPath(neighbor);
                    }
                    neighbor.isOnFrontier = true;
                    frontier.add(neighbor);
                }
            }
        }

        return null;
    }

    private float movementCost(PathNode neighbor, PathNode currentNode) {
        float dx = Math.abs(neighbor.x() - currentNode.x());
        float dy = Math.abs(neighbor.y() - currentNode.y());
        float dz = Math.abs(neighbor.z() - currentNode.z());
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    private float heuristic(PathNode node, PathNode nextNode) {
        float dx = Math.abs(node.x() - nextNode.x());
        float dy = Math.abs(node.y() - nextNode.y());
        float dz = Math.abs(node.z() - nextNode.z());
        return D * (dx + dy + dz);
    }

}
