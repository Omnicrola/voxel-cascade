package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.omnicrola.util.Vec3i;
import com.omnicrola.voxel.data.level.TerrainDefinition;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.fx.MaterialToken;
import com.omnicrola.voxel.jme.wrappers.impl.JmeGameContainer;
import com.omnicrola.voxel.terrain.*;
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
    private MaterialRepository materialRepository;
    private VoxelTypeLibrary voxelTypeLibrary;

    public VoxelTerrainState(VoxelTerrainGenerator voxelTerrainGenerator) {
        this.voxelTerrainGenerator = voxelTerrainGenerator;
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;
        this.voxelChunkHandler = buildVoxelChunkHandler(voxelGameEngine);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.voxelChunkHandler.update();
    }

    private VoxelChunkHandler buildVoxelChunkHandler(VoxelGameEngine voxelGameEngine) {
        JmeGameContainer jmeGameContainer = new JmeGameContainer(voxelGameEngine);
        this.materialRepository = new MaterialRepository(voxelGameEngine.getAssetManager());
        TerrainQuadFactory quadFactory = new TerrainQuadFactory(materialRepository);
        VoxelChunkRebuilder voxelChunkRebuilder = new VoxelChunkRebuilder(quadFactory, jmeGameContainer.physics(), jmeGameContainer.world());
        this.voxelTypeLibrary = new VoxelTypeLibrary();
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

    @Override
    public Effect buildPlaceholderVoxel(Vector3f location) {
        Box box = new Box(0.5f, 0.5f, 0.5f);
        Geometry voxel = new Geometry("voxel", box);
        Material material = this.materialRepository.get(MaterialToken.TERRAIN_PLACEHOLDER);
        voxel.setMaterial(material);
        voxel.setLocalTranslation(location);
        return new Effect(voxel);
    }

    @Override
    public IVoxelType getVoxelType(byte type) {
        return this.voxelTypeLibrary.lookup(type);
    }

    @Override
    public void setVoxel(Vec3i location, byte voxelType) {
        this.voxelChunkHandler.set(location, voxelType);
    }
}
