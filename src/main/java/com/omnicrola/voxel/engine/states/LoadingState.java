package com.omnicrola.voxel.engine.states;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.omnicrola.voxel.jme.wrappers.GuiBuilder;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public class LoadingState extends VoxelGameState {

    private GLabel loadingText;
    private IGameContainer gameContainer;

    @Override
    protected void voxelInitialize(IGameContainer gameContainer) {
        this.gameContainer = gameContainer;

        this.loadingText = this.gameContainer.guiBuilder().buildLabel("Loading...", ColorRGBA.White);
        loadingText.setTextPosition(300, 300);
        setEnabled(true);
    }

    @Override
    protected void voxelEnable(IGameContainer gameContainer) {
        this.gameContainer.setMouseGrabbed(false);
        this.gameContainer.attachToGui(this.loadingText);
    }

    @Override
    protected void voxelDisable(IGameContainer gameContainer) {
        this.gameContainer.attachToGui(this.loadingText);
    }
}
