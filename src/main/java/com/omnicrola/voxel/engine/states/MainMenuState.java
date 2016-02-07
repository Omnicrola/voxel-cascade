package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 1/15/2016.
 */
public class MainMenuState extends VoxelGameState {


    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        setEnabled(false);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.gui().changeScreens(UiScreen.MAIN_MENU);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
    }
}
