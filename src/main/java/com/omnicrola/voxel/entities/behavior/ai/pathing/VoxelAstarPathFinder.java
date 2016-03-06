package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by omnic on 3/5/2016.
 */
public class VoxelAstarPathFinder {

    private ITerrainManager terrainManager;

    public VoxelAstarPathFinder(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    public NavigationPath findPath(Vector3f currentLocation, Vector3f targetLocation) {
        VoxelData startVoxel = this.terrainManager.getVoxelAt(currentLocation);
        VoxelData targetVoxel = this.terrainManager.getVoxelAt(targetLocation);

        Frontier frontier = new Frontier(this.terrainManager, currentLocation, targetLocation);
        TerrainGraph terrainGraph = new TerrainGraph();

        while (frontier.hasNext()) {
            VoxelData current = frontier.next();
            System.out.println("next frontier node: " + current.getLocation());
            this.terrainManager.getNeighborsOf(current)
                    .stream()
                    .filter(v -> terrainGraph.doesNotContain(v))
                    .forEach(v -> terrainGraph.setSource(v, current));
            if (current.equals(targetVoxel)) {
                break;
            }
        }

        return terrainGraph.getPath(startVoxel, targetVoxel);
    }
}
