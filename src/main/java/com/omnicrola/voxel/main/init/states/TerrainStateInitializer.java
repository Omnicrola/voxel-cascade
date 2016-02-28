package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.jme3.bullet.PhysicsSpace;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.states.VoxelTerrainState;
import com.omnicrola.voxel.terrain.TerrainAdapter;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.build.PerlinNoiseGenerator;
import com.omnicrola.voxel.terrain.build.TerrainQuadFactory;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class TerrainStateInitializer implements IStateInitializer {

    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        WorldManager worldManager = initializationContainer.getWorldManager();
        VoxelTypeLibrary voxelTypeLibrary = initializationContainer.getVoxelTypeLibrary();
        MaterialRepository materialRepository = initializationContainer.getMaterialRepository();
        PhysicsSpace physicsSpace = initializationContainer.getPhysicsSpace();

        TerrainAdapter terrainAdapter = new TerrainAdapter(worldManager, materialRepository, voxelTypeLibrary, physicsSpace);
        VoxelChunkHandler voxelChunkHandler = buildVoxelChunkHandler(worldManager, materialRepository, terrainAdapter);
        VoxelTerrainGenerator voxelTerrainGenerator = buildTerrainGenerator(voxelTypeLibrary);

        VoxelTerrainState voxelTerrainState = new VoxelTerrainState(voxelTerrainGenerator, voxelChunkHandler);

        return voxelTerrainState;
    }

    private VoxelTerrainGenerator buildTerrainGenerator(VoxelTypeLibrary voxelTypeLibrary) {
        PerlinNoiseGenerator perlinNoiseGenerator = new PerlinNoiseGenerator();
        return new VoxelTerrainGenerator(perlinNoiseGenerator, voxelTypeLibrary);
    }

    private VoxelChunkHandler buildVoxelChunkHandler(WorldManager worldManager, MaterialRepository materialRepository, TerrainAdapter terrainAdapter) {
        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, worldManager);

        return new VoxelChunkHandler(terrainAdapter, voxelChunkRebuilder);
    }
}
