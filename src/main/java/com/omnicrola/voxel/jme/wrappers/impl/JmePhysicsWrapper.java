package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGamePhysics;

/**
 * Created by omnic on 1/16/2016.
 */
public class JmePhysicsWrapper implements IGamePhysics {
    private final PhysicsSpace physicsSpace;

    public JmePhysicsWrapper(PhysicsSpace physicsSpace) {
        this.physicsSpace = physicsSpace;
    }

    @Override
    public void addControl(PhysicsControl physicsControl) {
        this.physicsSpace.add(physicsControl);
    }

    @Override
    public void remove(Spatial spatial) {
        PhysicsControl control = spatial.getControl(PhysicsControl.class);
        if (control != null) {
            this.physicsSpace.remove(spatial);
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
            this.physicsSpace.add(spatial);
        }
        if (spatial instanceof Node) {
            ((Node) spatial)
                    .getChildren()
                    .stream()
                    .forEach(child -> add(child));
        }
    }

    @Override
    public Vector3f getGravity() {
        return this.physicsSpace.getGravity(new Vector3f());
    }
}
