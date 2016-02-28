package com.omnicrola.voxel.main.init.states;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.PhysicsSpace;
import com.omnicrola.voxel.engine.ITickProvider;
import com.omnicrola.voxel.engine.MaterialRepository;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.terrain.VoxelTypeLibrary;
import com.omnicrola.voxel.ui.Cursor2dProvider;
import com.omnicrola.voxel.world.WorldManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class InitializationContainer {
    private WorldManager worldManager;
    private VoxelTypeLibrary voxelTypeLibrary;
    private MaterialRepository materialRepository;
    private VoxelGameEngine voxelGameEngine;
    private Cursor2dProvider cursorProvider;

    public InitializationContainer(WorldManager worldManager,
                                   VoxelTypeLibrary voxelTypeLibrary,
                                   MaterialRepository materialRepository,
                                   PhysicsSpace physicsSpace) {
        this.worldManager = worldManager;
        this.voxelTypeLibrary = voxelTypeLibrary;
        this.materialRepository = materialRepository;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public VoxelTypeLibrary getVoxelTypeLibrary() {
        return voxelTypeLibrary;
    }

    public MaterialRepository getMaterialRepository() {
        return materialRepository;
    }

    public PhysicsSpace getPhysicsSpace() {
        return voxelGameEngine.getPhysicsSpace();
    }

    public Cursor2dProvider getCursorProvider() {
        return cursorProvider;
    }

    public AssetManager getAssetManager() {
        return voxelGameEngine.getAssetManager();
    }

    public ITickProvider getTicProvider() {
        return voxelGameEngine.getTicProvider();
    }
}
