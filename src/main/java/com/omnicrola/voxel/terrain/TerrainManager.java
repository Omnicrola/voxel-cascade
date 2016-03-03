package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 2/28/2016.
 */
public class TerrainManager implements ITerrainManager {

    private VoxelChunkHandler voxelChunkHandler;
    private VoxelTerrainGenerator voxelTerrainGenerator;

    public TerrainManager(VoxelChunkHandler voxelChunkHandler, VoxelTerrainGenerator voxelTerrainGenerator) {
        this.voxelChunkHandler = voxelChunkHandler;
        this.voxelTerrainGenerator = voxelTerrainGenerator;
    }

    @Override
    public void globalReset() {
        this.voxelChunkHandler.clearAll();
    }

    @Override
    public void load(TerrainDefinition terrain) {
        this.voxelTerrainGenerator.generate(terrain, this.voxelChunkHandler);
    }

    @Override
    public void setVoxel(Vec3i location, byte voxelType) {
        this.voxelChunkHandler.set(location, voxelType);
    }

    @Override
    public VoxelData getVoxelAt(Vector3f location) {
        return this.voxelChunkHandler.getVoxelAt(Vec3i.round(location));
    }

    @Override
    public boolean isBelowTerrain(Geometry geometry) {
        Vector3f worldLocation = geometry.getWorldTranslation();
        Vec3i location = Vec3i.floor(worldLocation.add(0, 0.5f, 0));
        return this.voxelChunkHandler.isVoxelSolidAt(location);
    }

    @Override
    public Vector3f getLowestNonSolidVoxel(Vector3f location) {
        return this.voxelChunkHandler.findLowestNonSolidVoxel(location);
    }

    @Override
    public Vector3f getHighestSolidVoxel(Vector3f location) {
        return this.voxelChunkHandler.findHighestSolidVoxel(location);
    }

    public void globalRebuild() {
        this.voxelChunkHandler.flagAllChunksForRebuild();
    }

    public void update(float tpf) {
        this.voxelChunkHandler.update();
    }
}
