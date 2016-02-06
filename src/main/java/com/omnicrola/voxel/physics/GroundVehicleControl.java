package com.omnicrola.voxel.physics;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.jme.wrappers.IGameWorld;

/**
 * Created by omnic on 1/23/2016.
 */
public class GroundVehicleControl extends RigidBodyControl {

    private static BoxCollisionShape getShape() {
        return new BoxCollisionShape(new Vector3f(0.5f, 0.25f, 0.5f));
    }

    private IGameWorld gameWorld;
    private Geometry geometry;

    public GroundVehicleControl(IGameWorld gameWorld, float mass) {
        super(getShape(), mass);
        this.gameWorld = gameWorld;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.geometry = (Geometry) spatial;
    }

    @Override
    public void setPhysicsSpace(PhysicsSpace space) {
        super.setPhysicsSpace(space);
        this.setLinearDamping(1);
//        this.setKinematic(true);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        if (this.gameWorld.isBelowTerrain(this.geometry)) {
            Vector3f physicsLocation = this.getPhysicsLocation();
            this.setPhysicsLocation(physicsLocation.setY((float) Math.floor(physicsLocation.y + 1)));
        }
        if (this.getLinearVelocity().length() > 0) {
            Quaternion physicsRotation = this.getPhysicsRotation();
            physicsRotation.lookAt(this.getLinearVelocity(), Vector3f.UNIT_Y);
            this.setPhysicsRotation(physicsRotation);
        } else {
            Quaternion physicsRotation = this.getPhysicsRotation();
            physicsRotation.lookAt(Vector3f.UNIT_Z, Vector3f.UNIT_Y);
            this.setPhysicsRotation(physicsRotation);
        }
    }
}
