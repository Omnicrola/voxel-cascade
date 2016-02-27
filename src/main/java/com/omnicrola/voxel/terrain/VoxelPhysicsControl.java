package com.omnicrola.voxel.terrain;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/31/2016.
 */
public class VoxelPhysicsControl extends RigidBodyControl {

    private Geometry geometry;
    private Vector3f chunkLocation;

    public VoxelPhysicsControl(Vector3f position, Spatial batchNode) {
        super(0);
        this.chunkLocation = position;
        if (batchNode instanceof Node) {
            Spatial child = ((Node) batchNode).getChild(0);
            if (child instanceof Geometry) {
                this.geometry = (Geometry) child;
            } else {
                throwGeometryException();
            }
        } else {
            throwGeometryException();
        }
    }

    private void throwGeometryException() {
        throw new IllegalStateException(this.getClass().getSimpleName() + " requires a geometry.");
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        super.setPhysicsLocation(this.chunkLocation);
        this.setCollisionShape(new MeshCollisionShape(this.geometry.getMesh()));
    }

}
