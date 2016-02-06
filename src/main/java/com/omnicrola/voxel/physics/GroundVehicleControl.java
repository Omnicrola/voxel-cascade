package com.omnicrola.voxel.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
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
    private boolean applyLocal;
    private Quaternion tmp_inverseWorldRotation = new Quaternion();
    private boolean useViewDirection;
    private Vector3f walkDirection = new Vector3f();
    private Vector3f viewDirection = new Vector3f(Vector3f.UNIT_Y);

    public GroundVehicleControl(IGameWorld gameWorld, float mass) {
        super(getShape(), mass);
        this.gameWorld = gameWorld;
        this.useViewDirection = true;
        this.applyLocal = false;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        setCcdMotionThreshold(0.5f);
    }

    public void update(float tpf) {
        super.update(tpf);
        if (this.enabled && this.getPhysicsSpace() != null) {
            this.applyCentralForce(this.walkDirection);
            this.getPhysicsRotation().lookAt(this.viewDirection, Vector3f.UNIT_Y);
        }
    }

    public Vector3f getWalkDirection() {
        return walkDirection;
    }

    public void setWalkDirection(Vector3f walkDirection) {
        this.walkDirection = walkDirection;
    }

    public void setViewDirection(Vector3f viewDirection) {
        this.viewDirection = viewDirection;
    }
}
