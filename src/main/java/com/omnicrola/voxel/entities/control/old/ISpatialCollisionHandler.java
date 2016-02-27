package com.omnicrola.voxel.entities.control.old;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/17/2016.
 */
public interface ISpatialCollisionHandler {
    // each implementation of the collision handler only deals with the effects to
    // it's parent object. It should not modify the "otherObject", it's own CollisionControl will do that
    void collideWith(Spatial otherObject);
}
