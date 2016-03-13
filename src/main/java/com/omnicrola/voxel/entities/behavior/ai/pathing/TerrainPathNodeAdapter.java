package com.omnicrola.voxel.entities.behavior.ai.pathing;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 3/10/2016.
 */
public class TerrainPathNodeAdapter {
    private final HashMap<Vec3i, PathNode> positionNodeMap;
    private ITerrainManager terrainManager;

    public TerrainPathNodeAdapter(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
        this.positionNodeMap = new HashMap<>();
    }

    public void reset() {
        this.positionNodeMap.clear();
    }

    public PathNode getNodeAt(Vector3f location) {
        Vec3i gridLocation = Vec3i.fromVec3(location);
        return getNode(gridLocation);
    }

    public List<PathNode> getNeighbors(PathNode currentNode) {
        List<Vec3i> neighborLocations = generateGridLocationsFrom(currentNode.voxel.getGridLocation());
        return neighborLocations.stream()
                .map(l -> getNode(l))
                .filter(n -> n.isViable)
                .filter(n -> isReachable(n, currentNode))
                .collect(Collectors.toList());
    }

    private boolean isReachable(PathNode sourceNode, PathNode nextNode) {
        int y1 = sourceNode.voxel.getGridLocation().getY();
        int y2 = nextNode.voxel.getGridLocation().getY();
        int difference = Math.abs(y1 - y2);
        if (difference == 0) {
            return true;
        }
        if(sourceNode.voxel.isHalf() || nextNode.voxel.isHalf()){
            return true;
        }
        return false;
    }

    private List<Vec3i> generateGridLocationsFrom(Vec3i gridLocation) {
        ArrayList<Vec3i> locations = new ArrayList<>();
        addSlice(gridLocation, locations, -1);
        addSlice(gridLocation, locations, 0);
        addSlice(gridLocation, locations, 1);
        return locations;
    }

    private void addSlice(Vec3i gridLocation, ArrayList<Vec3i> locations, int y) {
        locations.add(gridLocation.translate(-1, y, -1));
        locations.add(gridLocation.translate(-1, y, 0));
        locations.add(gridLocation.translate(-1, y, 1));

        locations.add(gridLocation.translate(0, y, -1));
        locations.add(gridLocation.translate(0, y, 0));
        locations.add(gridLocation.translate(0, y, 1));

        locations.add(gridLocation.translate(1, y, -1));
        locations.add(gridLocation.translate(1, y, 0));
        locations.add(gridLocation.translate(1, y, 1));
    }

    private PathNode getNode(Vec3i gridLocation) {
        if (!this.positionNodeMap.containsKey(gridLocation)) {
            VoxelData voxel = this.terrainManager.getVoxelAt(gridLocation);
            boolean isEmpty = !voxel.getType().isSolid() || voxel.isHalf();
            VoxelData floorVoxel = this.terrainManager.getVoxelAt(gridLocation.translate(0, -1, 0));
            boolean hasSolidFloor = floorVoxel.getType().isSolid();

            PathNode pathNode = new PathNode(voxel);
            pathNode.isViable = (isEmpty && hasSolidFloor);
            this.positionNodeMap.put(gridLocation, pathNode);
            return pathNode;
        }
        return this.positionNodeMap.get(gridLocation);
    }

    public Collection<PathNode> getAll() {
        return this.positionNodeMap.values();
    }
}
