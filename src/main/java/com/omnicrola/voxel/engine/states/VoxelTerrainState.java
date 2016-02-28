package com.omnicrola.voxel.engine.states;

import com.jme3.app.state.AbstractAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.data.VoxelData;

/**
 * Created by Eric on 2/22/2016.
 */
public class VoxelTerrainState extends AbstractAppState implements ITerrainManager {

    private VoxelChunkHandler voxelChunkHandler;
    private VoxelTerrainGenerator voxelTerrainGenerator;

    public VoxelTerrainState(VoxelTerrainGenerator voxelTerrainGenerator, VoxelChunkHandler voxelChunkHandler) {
        this.voxelTerrainGenerator = voxelTerrainGenerator;
        this.voxelChunkHandler = voxelChunkHandler;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.voxelChunkHandler.update();
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

    public void globalRebuild() {
        this.voxelChunkHandler.flagAllChunksForRebuild();
    }
}
