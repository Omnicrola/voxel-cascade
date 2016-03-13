package com.omnicrola.voxel.terrain;

import com.jme3.math.Vector3f;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.data.VoxelData;

import java.util.Optional;

/**
 * Created by Eric on 2/28/2016.
 */
public class TerrainManager implements ITerrainManager {

    private VoxelChunkHandler voxelChunkHandler;
    private VoxelTerrainGenerator voxelTerrainGenerator;
    private VoxelTypeLibrary voxelTypeLibrary;

    public TerrainManager(VoxelChunkHandler voxelChunkHandler,
                          VoxelTerrainGenerator voxelTerrainGenerator,
                          VoxelTypeLibrary voxelTypeLibrary) {
        this.voxelChunkHandler = voxelChunkHandler;
        this.voxelTerrainGenerator = voxelTerrainGenerator;
        this.voxelTypeLibrary = voxelTypeLibrary;
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
        return this.voxelChunkHandler.getVoxelAt(Vec3i.fromVec3(location));
    }

    @Override
    public VoxelData getVoxelAt(Vec3i location) {
        return this.voxelChunkHandler.getVoxelAt(location);
    }

    @Override
    public Optional<VoxelData> findLowestEmptyVoxel(Vector3f location) {
        return this.voxelChunkHandler.findLowestEmptyVoxel(location);
    }

    @Override
    public Optional<VoxelData> getHighestSolidVoxel(Vector3f location) {
        return this.voxelChunkHandler.findHighestSolidVoxel(location);
    }

    @Override
    public IVoxelType getVoxelType(byte voxelType) {
        return this.voxelTypeLibrary.lookup(voxelType);
    }

    public void globalRebuild() {
        this.voxelChunkHandler.flagAllChunksForRebuild();
    }

    public void update(float tpf) {
        this.voxelChunkHandler.update();
    }
}
