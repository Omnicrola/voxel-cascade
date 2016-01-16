package com.omnicrola.voxel.jme.wrappers;

import com.omnicrola.voxel.engine.states.VoxelGameState;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameContainer {

    IGameGui gui();
    IGameWorld world();

    void enableState(Class<? extends VoxelGameState> stateClass);
    void disableState(Class<? extends VoxelGameState> stateClass);

    IGameInput input();

    IGamePhysics physics();
}
