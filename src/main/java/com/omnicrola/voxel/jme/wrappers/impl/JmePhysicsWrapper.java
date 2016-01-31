package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.bullet.control.PhysicsControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
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

    @Override
    public void remove(Spatial spatial) {
        PhysicsControl control = spatial.getControl(PhysicsControl.class);
        if (control != null) {
            this.game.getPhysicsSpace().remove(spatial);
        }
        if (spatial instanceof Node) {
            ((Node) spatial)
                    .getChildren()
                    .stream()
                    .forEach(child -> remove(child));
        }
    }

    @Override
    public void add(Spatial spatial) {
        PhysicsControl control = spatial.getControl(PhysicsControl.class);
        if (control != null) {
            this.game.getPhysicsSpace().add(spatial);
        }
        if (spatial instanceof Node) {
            ((Node) spatial)
                    .getChildren()
                    .stream()
                    .forEach(child -> add(child));
        }

    }
}
