package com.omnicrola.voxel.terrain;

import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.data.VoxelType;

/**
 * Created by Eric on 2/24/2016.
 */
public class VoxelTerrainGenerator {

    PerlinNoiseGenerator perlinNoiseGenerator;

    public VoxelTerrainGenerator(PerlinNoiseGenerator perlinNoiseGenerator) {
        this.perlinNoiseGenerator = perlinNoiseGenerator;
    }

    public void generate(TerrainDefinition terrainDefinition, VoxelChunkHandler voxelChunkHandler) {
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

        if (Math.random() < 0.125) {
            voxelChunkHandler.setHalf(location, true);
        }
    }
}
