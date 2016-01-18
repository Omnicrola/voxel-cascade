package com.omnicrola.voxel.jme.wrappers;

import com.jme3.bullet.control.PhysicsControl;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IGamePhysics {
    void addControl(PhysicsControl physicsControl);

    void remove(Spatial spatial);

    void add(Spatial spatial);
}
