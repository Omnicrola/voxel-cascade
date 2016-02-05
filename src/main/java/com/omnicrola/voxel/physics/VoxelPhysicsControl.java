package com.omnicrola.voxel.physics;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelPhysicsControl extends RigidBodyControl {

    public VoxelPhysicsControl(Mesh mesh) {
        super(new MeshCollisionShape(mesh),1);
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
