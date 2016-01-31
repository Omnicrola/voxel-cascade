package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/31/2016.
 */
public class BetterSphereCollisionShape extends SphereCollisionShape {
    public BetterSphereCollisionShape(float size) {
        super(size);
    }

    @Override
    public void setScale(Vector3f scale) {

    }
}
