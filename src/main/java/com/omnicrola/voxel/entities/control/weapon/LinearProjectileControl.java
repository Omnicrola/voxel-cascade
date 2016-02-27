package com.omnicrola.voxel.entities.control.weapon;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.physics.BetterSphereCollisionShape;

/**
 * Created by omnic on 1/17/2016.
 */
public class LinearProjectileControl extends GhostControl {
    private float size;
    private Vector3f velocity;

    public LinearProjectileControl(float size, Vector3f velocity) {
        super(new BetterSphereCollisionShape(size));
        this.size = size;
        this.velocity = velocity;
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        setCcdMotionThreshold(this.size / 4);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        Vector3f adjustedVelocity = this.velocity.mult(tpf);
        Vector3f newPosition = this.spatial.getLocalTranslation().add(adjustedVelocity);
        this.spatial.setLocalTranslation(newPosition);
    }
}
