package com.omnicrola.voxel.terrain.build.operations;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.build.ITerrainOperation;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by Eric on 4/10/2016.
 */
public class AddHalfblockEdgesOperation implements ITerrainOperation {
    @Override
    public void perform(TerrainDefinition terrainDefinition, VoxelChunkHandler voxelChunkHandler) {
        int width = terrainDefinition.getWidth();
        int depth = terrainDefinition.getDepth();

        sweepXAxis(width, depth, voxelChunkHandler);
    }

    private void sweepXAxis(int width, int depth, VoxelChunkHandler voxelChunkHandler) {
        int halfX = width / 2;
        int halfZ = depth / 2;
        Vector3f position = new Vector3f();
        for (int x = -halfX; x < halfX; x++) {
            int previousHeight = 0;
            int currentHeight = 0;
            for (int z = -halfZ; z < halfZ; z++) {
                position.set(x, Float.MAX_VALUE, z);
                Optional<VoxelData> highestSolidVoxel = voxelChunkHandler.findHighestSolidVoxel(position);
                if (highestSolidVoxel.isPresent()) {
                    currentHeight = highestSolidVoxel.get().getGridLocation().getY();
                } else {
                    currentHeight = 0;
                }
                if (currentHeight != previousHeight) {
                    addHalfblock(voxelChunkHandler, x, z, currentHeight, previousHeight);
                }
                previousHeight = currentHeight;
            }
        }
    }

    private void addHalfblock(VoxelChunkHandler voxelChunkHandler, int x, int z, int currentHeight, int previousHeight) {
        int y = currentHeight > previousHeight ? currentHeight : previousHeight;
        Vec3i position = new Vec3i(x, y, z);
        voxelChunkHandler.setHalf(position, true);
    }
}
