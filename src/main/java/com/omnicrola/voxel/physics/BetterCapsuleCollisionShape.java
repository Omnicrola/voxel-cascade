package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.math.Vector3f;

/**
 * Created by Eric on 2/9/2016.
 */
public class BetterCapsuleCollisionShape extends CapsuleCollisionShape{
    public BetterCapsuleCollisionShape(float radius, float height) {
        super(radius, height);
    }

    @Override
    public void setScale(Vector3f scale) {
        // only purpose of this class is to override this method and prevent
        // the log from being spammed, since the parent class' setScale
        // is a no-op anyway
    }
}
