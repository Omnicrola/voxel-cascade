package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.IGameGui;

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

    @Override
    public void setCameraRotation(Quaternion rotation) {
        this.game.getCamera().setRotation(rotation);
    }

    @Override
    public void setCameraPosition(Vector3f position) {
        this.game.getCamera().setLocation(position);

    }


}
