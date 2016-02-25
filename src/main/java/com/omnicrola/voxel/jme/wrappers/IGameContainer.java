package com.omnicrola.voxel.jme.wrappers;

import com.jme3.app.state.AppState;
import com.jme3.asset.AssetManager;

/**
 * Created by omnic on 1/15/2016.
 */
@Deprecated
public interface IGameContainer extends IStateManager {

    IGameGui gui();

    IGameWorld world();


    IGameInput input();

    IGamePhysics physics();

    AssetManager getAssetManager();

    void addState(AppState voxelGameState);

    void quitAndExit();

    IGameNetwork network();


}
