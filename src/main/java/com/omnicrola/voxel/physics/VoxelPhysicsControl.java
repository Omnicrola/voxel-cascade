package com.omnicrola.voxel.physics;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelPhysicsControl extends RigidBodyControl {

    public VoxelPhysicsControl() {
        super(1);
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        this.setKinematic(true);
    }

    @Override
    public void setPhysicsLocation(Vector3f location) {
        super.setPhysicsLocation(location);
    }
}
