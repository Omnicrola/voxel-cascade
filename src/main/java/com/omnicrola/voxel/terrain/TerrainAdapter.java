package com.omnicrola.voxel.terrain;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.terrain.data.VoxelChunk;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/27/2016.
 */
public class TerrainAdapter {
    private WorldManager worldManager;
    private MaterialRepository materialRepository;
    private VoxelTypeLibrary voxelTypeLibrary;
    private PhysicsSpace physicsSpace;

    public TerrainAdapter(WorldManager worldManager,
                          MaterialRepository materialRepository,
                          VoxelTypeLibrary voxelTypeLibrary,
                          PhysicsSpace physicsSpace) {
        this.worldManager = worldManager;
        this.materialRepository = materialRepository;
        this.voxelTypeLibrary = voxelTypeLibrary;
        this.physicsSpace = physicsSpace;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public MaterialRepository getMaterialRepository() {
        return materialRepository;
    }

    public IVoxelType lookupVoxelType(byte voxel) {
        return this.voxelTypeLibrary.lookup(voxel);
    }

    public void addChunk(VoxelChunk chunk) {
        this.worldManager.addTerrainChunk(chunk);
    }

    public void attachQuad(Spatial terrainQuad) {
        this.physicsSpace.add(terrainQuad);
    }

    public void removeQuad(Spatial terrainQuad) {
        this.physicsSpace.remove(terrainQuad);
    }
}
