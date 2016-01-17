package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.WorldCursor;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;
import com.omnicrola.voxel.jme.wrappers.IGeometryBuilder;
import com.omnicrola.voxel.world.GeometryBuilder;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeWorldWrapper implements IGameWorld {
    private final GeometryBuilder geometryBuilder;
    private VoxelGameEngine game;

    public JmeWorldWrapper(VoxelGameEngine game) {
        this.game = game;
        this.geometryBuilder = new GeometryBuilder(game.getAssetManager());
    }

    @Override
    public void attach(Spatial node) {
        this.game.getRootNode().attachChild(node);
    }

    @Override
    public void remove(Spatial node) {
        this.game.getRootNode().detachChild(node);
    }

    @Override
    public IGeometryBuilder build() {
        return this.geometryBuilder;
    }

    @Override
    public void addLight(Light light) {
        this.game.getRootNode().addLight(light);
    }

    @Override
    public WorldCursor createCursor(Node terrain) {
        return new WorldCursor(this.game.getInputManager(), this.game.getCamera(), terrain);
    }
}
