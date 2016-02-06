package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.QuitActiveGameListener;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 2/6/2016.
 */
public class GameOverState extends VoxelGameState {

    private GLabel gameOverLabel;

    public GameOverState() {
    }

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        gameOverLabel = gameContainer.gui().build().label("Game Over", ColorRGBA.White);
        addStateInput(GameInputAction.SELECT, new QuitActiveGameListener(gameContainer));
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        gameContainer.gui().attach(gameOverLabel);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        gameContainer.gui().remove(gameOverLabel);
    }
}
