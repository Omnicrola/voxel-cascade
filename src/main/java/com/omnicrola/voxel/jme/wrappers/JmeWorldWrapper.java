package com.omnicrola.voxel.jme.wrappers;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeWorldWrapper implements IGameWorld{
    private VoxelGameEngine game;

    public JmeWorldWrapper(VoxelGameEngine game) {
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

}
