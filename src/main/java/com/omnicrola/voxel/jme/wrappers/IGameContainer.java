package com.omnicrola.voxel.jme.wrappers;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;
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

    AssetManager getAssetManager();

    void addState(AppState voxelGameState);

    <T extends AppState> T getState(Class<T> activePlayStateClass);
}
