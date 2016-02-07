package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.QuitActiveGameListener;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.UiScreen;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends VoxelGameState {

    private IGameContainer gameContainer;

    public GameOverState() {
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        addStateInput(GameInputAction.SELECT, new QuitActiveGameListener(gameContainer));
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.gui().changeScreens(UiScreen.GAME_OVER);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {

    }
}
