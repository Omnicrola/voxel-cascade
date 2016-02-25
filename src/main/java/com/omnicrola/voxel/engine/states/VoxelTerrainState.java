package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.impl.JmeGameContainer;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.VoxelChunkHandler;
import com.omnicrola.voxel.terrain.VoxelTerrainGenerator;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.terrain.build.TerrainQuadFactory;
import com.omnicrola.voxel.terrain.build.VoxelChunkRebuilder;
import com.omnicrola.voxel.terrain.data.VoxelType;

import java.util.Arrays;

/**
 * Created by Eric on 2/22/2016.
 */
public class VoxelTerrainState extends AbstractAppState implements ITerrainManager {

    private VoxelChunkHandler voxelChunkHandler;
    private VoxelTerrainGenerator voxelTerrainGenerator;

    public VoxelTerrainState(VoxelTerrainGenerator voxelTerrainGenerator) {
        this.voxelTerrainGenerator = voxelTerrainGenerator;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.voxelChunkHandler = buildVoxelChunkHandler(app);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.voxelChunkHandler.update();
    }

    private VoxelChunkHandler buildVoxelChunkHandler(Application app) {
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        JmeGameContainer jmeGameContainer = new JmeGameContainer(voxelGameEngine);
        TerrainQuadFactory quadFactory = new TerrainQuadFactory(new MaterialRepository(app.getAssetManager()));
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, jmeGameContainer.physics(), jmeGameContainer.world());
        VoxelTypeLibrary voxelTypeLibrary = new VoxelTypeLibrary();
        Arrays.asList(VoxelType.values()).forEach(t -> voxelTypeLibrary.addType(t));
        return new VoxelChunkHandler(voxelTypeLibrary, voxelChunkRebuilder);
    }

    @Override
    public void globalReset() {
        this.voxelChunkHandler.clearAll();
    }

    @Override
    public void load(TerrainDefinition terrain) {
        this.voxelTerrainGenerator.generate(terrain, this.voxelChunkHandler);
    }
}
