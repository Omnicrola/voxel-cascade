package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.VoxelTickProvider;
import com.omnicrola.voxel.world.*;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldManagerState extends AbstractAppState {

    private  WorldCommandProcessor worldCommandProcessor;
    private VoxelTickProvider ticProvider;
    private VoxelTerrainState voxelTerrainState;

    public WorldManagerState(VoxelTerrainState voxelTerrainState) {
        this.voxelTerrainState = voxelTerrainState;
    }

    public void addCommand(IWorldCommand worldCommand) {
        this.worldCommandProcessor.addCommand(worldCommand);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        VoxelGameEngine voxelGameEngine = (VoxelGameEngine) app;

        WorldEntityBuilder worldEntityBuilder = new WorldEntityBuilder(voxelGameEngine);
        WorldEntityManager worldEntityManager = new WorldEntityManager();
        CommandPackage commandPackage = new CommandPackage(worldEntityBuilder, worldEntityManager, this.voxelTerrainState);
        this.worldCommandProcessor = new WorldCommandProcessor(commandPackage);
        this.ticProvider = new VoxelTickProvider();
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        this.worldCommandProcessor.execute(ticProvider.getTic());
    }
}
