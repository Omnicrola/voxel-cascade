package com.omnicrola.voxel.engine.states;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class ActivePlayState extends VoxelGameState {


    private IGameContainer gameContainer;
    private Node stateRoot;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;
        this.stateRoot = new Node();
        GLabel label = gameContainer.gui().build().label("You are playing", ColorRGBA.Cyan);
        label.setTextPosition(300, 300);
        stateRoot.attachChild(label);
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
