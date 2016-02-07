package com.omnicrola.voxel.jme.wrappers;

import com.jme3.app.state.AppState;
import com.omnicrola.voxel.engine.states.VoxelGameState;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IStateManager {
    void enableState(Class<? extends VoxelGameState> stateClass);

    void disableState(Class<? extends VoxelGameState> stateClass);

    <T extends AppState> T getState(Class<T> activePlayStateClass);

}
