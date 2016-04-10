package com.omnicrola.voxel.terrain.build.operations;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.build.ITerrainOperation;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.data.VoxelType;

/**
 * Created by Eric on 4/10/2016.
 */
public class CreateRandomTerrainOperation implements ITerrainOperation {
    PerlinNoiseGenerator perlinNoiseGenerator;

    public CreateRandomTerrainOperation(PerlinNoiseGenerator perlinNoiseGenerator) {
        this.perlinNoiseGenerator = perlinNoiseGenerator;
    }

    @Override
    public void perform(TerrainDefinition terrainDefinition, VoxelChunkHandler voxelChunkHandler) {
        int width = terrainDefinition.getWidth();
        int depth = terrainDefinition.getDepth();
        this.perlinNoiseGenerator.setOctaves(terrainDefinition.getOctaves());
        this.perlinNoiseGenerator.setSeed(terrainDefinition.getSeed());
        float[][] noise = this.perlinNoiseGenerator.generate(width, depth);
        setVoxels(voxelChunkHandler, terrainDefinition, noise);
    }

    private void setVoxels(VoxelChunkHandler voxelChunkHandler, TerrainDefinition terrain, float[][] noise) {
        int halfWidth = terrain.getWidth() / 2;
        int halfDepth = terrain.getDepth() / 2;
        for (int x = 0; x < noise.length; x++) {
            for (int z = 0; z < noise[x].length; z++) {
                float height = noise[x][z] * terrain.getVerticalScale();
                fillZ(voxelChunkHandler, x - halfWidth, z - halfDepth, height);
            }
        }
    }

    private void fillZ(VoxelChunkHandler voxelChunkHandler, int x, int z, float height) {
        int y;
        Vec3i location = new Vec3i();

        for (y = 0; y <= height; y++) {
            location = new Vec3i(x, y, z);
            voxelChunkHandler.set(location, VoxelType.GREY.uniqueId());
            voxelChunkHandler.setResource(location, 1f);
        }
    }
}
