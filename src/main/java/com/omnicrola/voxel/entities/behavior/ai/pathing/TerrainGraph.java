package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by omnic on 3/5/2016.
 */
public class TerrainGraph {

    private final HashMap<VoxelData, VoxelData> graph;

    public TerrainGraph() {
        this.graph = new HashMap<>();
    }

    public boolean doesNotContain(VoxelData voxel) {
        return !this.graph.containsKey(voxel);
    }

    public void setSource(VoxelData node, VoxelData cameFrom) {
        System.out.println("added to graph: " + node.getLocation() + " " + cameFrom.getLocation());
        this.graph.put(node, cameFrom);
    }

    public NavigationPath getPath(VoxelData startVoxel, VoxelData targetVoxel) {
        System.out.println("getPath from : " + targetVoxel.getLocation());
        ArrayList<Vector3f> pathNodes = new ArrayList<>();
        pathNodes.add(targetVoxel.getLocation());

        VoxelData current = targetVoxel;
        while (!startVoxel.getLocation().equals(current.getLocation())) {
            System.out.println("pathing...");
            current = this.graph.get(current);
            pathNodes.add(current.getLocation());
        }

        return new NavigationPath(pathNodes);
    }
}
