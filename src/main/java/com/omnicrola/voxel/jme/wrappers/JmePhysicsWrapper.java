package com.omnicrola.voxel.jme.wrappers;

import com.jme3.bullet.control.PhysicsControl;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/16/2016.
 */
public class JmePhysicsWrapper implements IGamePhysics{
    private VoxelGameEngine game;

    public JmePhysicsWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void addControl(PhysicsControl physicsControl) {
        this.game.getPhysicsSpace().add(physicsControl);
    }
}
