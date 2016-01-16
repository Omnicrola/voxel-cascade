package com.omnicrola.voxel.jme.wrappers;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.input.FlyByCamera;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeApplicationWrapper implements IGameContainer {
    private final VoxelGameEngine game;

    public JmeApplicationWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void attachToWorld(Spatial node) {
        this.game.getRootNode().attachChild(node);
    }

    @Override
    public void removeFromWorld(Spatial node) {
        this.game.getRootNode().detachChild(node);
    }

    @Override
    public GuiBuilder guiBuilder() {
        return new GuiBuilder(this.game);
    }

    @Override
    public void attachToGui(Spatial node) {
        this.game.getGuiNode().attachChild(node);
    }

    @Override
    public void removeFromGui(Spatial node) {
        this.game.getGuiNode().detachChild(node);
    }

    @Override
    public void setMouseGrabbed(boolean isGrabbed) {
        this.game.getInputManager().setCursorVisible(isGrabbed);
        FlyByCamera flyByCamera = this.game.getFlyByCamera();
        flyByCamera.setDragToRotate(!isGrabbed);
        flyByCamera.setEnabled(isGrabbed);
    }
}
