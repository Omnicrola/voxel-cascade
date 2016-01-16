package com.omnicrola.voxel.jme.wrappers;

import com.jme3.input.FlyByCamera;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeGuiWrapper implements IGameGui {
    private VoxelGameEngine game;

    public JmeGuiWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public GuiBuilder build() {
        return new GuiBuilder(this.game);
    }

    @Override
    public void attach(Spatial node) {
        this.game.getGuiNode().attachChild(node);
    }

    @Override
    public void remove(Spatial node) {
        this.game.getGuiNode().detachChild(node);
    }




}
