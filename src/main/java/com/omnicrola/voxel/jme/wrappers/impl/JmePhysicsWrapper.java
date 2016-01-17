package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.bullet.control.PhysicsControl;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;

/**
 * Created by omnic on 1/16/2016.
 */
public class JmePhysicsWrapper implements IGamePhysics {
    private VoxelGameEngine game;

    public JmePhysicsWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void addControl(PhysicsControl physicsControl) {
        this.game.getPhysicsSpace().add(physicsControl);
    }
}
