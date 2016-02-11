package com.omnicrola.voxel.entities.control;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.omnicrola.voxel.physics.BetterSphereCollisionShape;

/**
 * Created by Eric on 2/9/2016.
 */
public class ParabolicProjectileControl extends RigidBodyControl {
    private float size;
    private final Vector3f trajectory;
    private final Vector3f gravity;

    public ParabolicProjectileControl(float size, Vector3f trajectory, Vector3f gravity) {
        super(new BetterSphereCollisionShape(size));
        this.size = size;
        this.trajectory = trajectory;
        this.gravity = gravity;
    }


    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        this.setLinearVelocity(this.trajectory);
        setCcdMotionThreshold(this.size / 4);
    }

    @Override
    public void update(float frameDelta) {
        super.update(frameDelta);
//        Vector3f translationForThisFrame = this.trajectory.add(this.gravity.mult(frameDelta));
//        Vector3f newPosition = this.spatial.getLocalTranslation().add(translationForThisFrame);
//        this.spatial.setLocalTranslation(newPosition);
    }
}
