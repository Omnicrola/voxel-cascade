package com.omnicrola.voxel.jme.wrappers;

import com.jme3.bullet.control.PhysicsControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/16/2016.
 */
@Deprecated
public interface IGamePhysics {
    void addControl(PhysicsControl physicsControl);

    void remove(Spatial spatial);

    void add(Spatial spatial);

    Vector3f getGravity();
}
