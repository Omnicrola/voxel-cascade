package com.omnicrola.voxel.terrain.build;

import com.jme3.math.Vector3f;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.terrain.highlight.ITerrainHighlightStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 3/4/2016.
 */
public class BuildTerrainHighlightStrategy implements ITerrainHighlightStrategy {
    private ITerrainManager terrainManager;

    public BuildTerrainHighlightStrategy(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    @Override
    public List<VoxelData> findAllVoxelsInSelection(Vector3f startLocation, Vector3f endLocation) {
        float startX = Math.min(startLocation.x, endLocation.getX());
        float startZ = Math.min(startLocation.z, endLocation.getZ());
        float endX = Math.max(startLocation.x, endLocation.getX());
        float endZ = Math.max(startLocation.z, endLocation.getZ());
        Vector3f location = new Vector3f();

        ArrayList<VoxelData> voxels = new ArrayList<>();
        for (float x = startX; x <= endX; x++) {
            for (float z = startZ; z <= endZ; z++) {
                location.set(x, startLocation.y, z);
                Optional<VoxelData> highestSolidVoxel = this.terrainManager.findLowestEmptyVoxel(location);
                if (highestSolidVoxel.isPresent()) {
                    voxels.add(highestSolidVoxel.get());
                }
            }
        }
        return voxels;
    }
}
