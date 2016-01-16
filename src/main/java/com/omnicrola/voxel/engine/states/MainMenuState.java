package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class MainMenuState extends VoxelGameState {


    private IGameContainer gameContainer;
    private Node stateRoot;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.stateRoot = new Node();
        GLabel titleText = this.gameContainer.gui().build().label("Main Menu", ColorRGBA.Green);
        titleText.setTextPosition(300,300);
        stateRoot.attachChild(titleText);
        setEnabled(false);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.gameContainer.gui().attach(this.stateRoot);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        gameContainer.gui().remove(this.stateRoot);
    }
}
