package com.omnicrola.voxel.entities.control;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/17/2016.
 */
public class LinearProjectileControl extends GhostControl {
    private Vector3f velocity;

    public LinearProjectileControl(Vector3f velocity) {
        super(new SphereCollisionShape(0.0125f));

        this.velocity = velocity;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector3f adjustedVelocity = this.velocity.mult(tpf);
        Vector3f newPosition = this.spatial.getLocalTranslation().add(adjustedVelocity);
        this.spatial.setLocalTranslation(newPosition);
    }
}
