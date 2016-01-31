package com.omnicrola.voxel.physics;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelPhysicsControl extends RigidBodyControl {
    private Vector3f location;

    public VoxelPhysicsControl() {
        super(0);
        this.location = new Vector3f();
    }

    @Override
    public void setPhysicsLocation(Vector3f location) {
        this.location = location;
        super.setPhysicsLocation(location);
    }
}
