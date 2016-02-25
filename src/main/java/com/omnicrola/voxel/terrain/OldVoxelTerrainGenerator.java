package com.omnicrola.voxel.terrain;

import com.jme3.scene.Node;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.data.VoxelType;

/**
 * Created by omnic on 1/16/2016.
 */
public class OldVoxelTerrainGenerator {

    private ChunkHandlerFactory chunkHandlerFactory;
    private PerlinNoiseGenerator perlinNoiseGenerator;

    public OldVoxelTerrainGenerator(ChunkHandlerFactory chunkHandlerFactory, PerlinNoiseGenerator perlinNoiseGenerator) {
        this.chunkHandlerFactory = chunkHandlerFactory;
        this.perlinNoiseGenerator = perlinNoiseGenerator;
    }

    public Node load(LevelDefinition levelData, VoxelTypeLibrary voxelTypeLibrary) {
        Node terrainRoot = new Node("Terrain");
        VoxelChunkHandler voxelChunkHandler = this.chunkHandlerFactory.build(voxelTypeLibrary);

        TerrainDefinition terrainDefinition = levelData.getTerrain();
        int width = terrainDefinition.getWidth();
        int depth = terrainDefinition.getDepth();
        this.perlinNoiseGenerator.setOctaves(terrainDefinition.getOctaves());
        this.perlinNoiseGenerator.setSeed(terrainDefinition.getSeed());
        float[][] noise = this.perlinNoiseGenerator.generate(width, depth);
        setVoxels(voxelChunkHandler, terrainDefinition, noise);

        terrainRoot.addControl(new VoxelTerrainControl(voxelChunkHandler));
        return terrainRoot;
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
        for (int y = 0; y <= height; y++) {
            Vec3i location = new Vec3i(x, y, z);
//            byte voxelType = (Math.random() <0.5) ? VoxelType.GREY.uniqueId() : VoxelType.BLUE.uniqueId();
            voxelChunkHandler.set(location, VoxelType.GREY.uniqueId());
            voxelChunkHandler.setResource(location, 1f);
        }
    }
}
