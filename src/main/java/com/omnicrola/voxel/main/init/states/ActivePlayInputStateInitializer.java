package com.omnicrola.voxel.main.init.states;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.terrain.TerrainManager;

/**
 * Created by omnic on 2/28/2016.
 */
public class ActivePlayInputStateInitializer implements IStateInitializer {
    @Override
    public AppState buildState(InitializationContainer initializationContainer) {
        LevelManager levelManager = initializationContainer.getLevelManager();
        TerrainManager terrainManager = initializationContainer.getTerrainManager();
        return new ActivePlayState(levelManager, terrainManager);
    }
}
