package com.omnicrola.voxel.jme.wrappers;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.states.MainMenuState;
import com.omnicrola.voxel.engine.states.VoxelGameState;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameContainer {

    IGameGui gui();
    IGameWorld world();

    void enableState(Class<? extends VoxelGameState> stateClass);
    void disableState(Class<? extends VoxelGameState> stateClass);

    IGameInput input();
}
