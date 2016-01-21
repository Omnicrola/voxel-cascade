package com.omnicrola.voxel.entities.control;

import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.util.VectorUtil;
import com.omnicrola.voxel.data.units.MovementDefinition;

/**
 * Created by omnic on 1/16/2016.
 */
public class MotionGovernorControl extends AbstractControl {

    public static final float DAMPENING = 0.0001f;
    public static final Vector3f MAX_VELOCITY = new Vector3f(2, 2, 2);

    private final Vector3f masterSteering;
    private final float floorOffset;
    private MovementDefinition movementDefinition;

    public MotionGovernorControl(MovementDefinition movementDefinition) {
        this.movementDefinition = movementDefinition;
        this.masterSteering = new Vector3f();
        this.floorOffset = 0.5f;
    }

    private void addSteering(Vector3f steering) {
        this.masterSteering.x += steering.x;
        this.masterSteering.y += steering.y;
        this.masterSteering.z += steering.z;
    }

    public boolean seekAndArriveAtRange(Vector3f targetPosition, float slowRadius, float minimumRange) {
        PhysicsRigidBody physicsControl = getPhysicsControl();
        Vector3f worldTranslation = this.spatial.getWorldTranslation();
        Vector3f desiredVelocity = targetPosition.subtract(worldTranslation);
        final float distance = desiredVelocity.length();
        final float adjustedDistance = distance - this.floorOffset;
        if (adjustedDistance < DAMPENING + minimumRange) {
            physicsControl.setLinearVelocity(new Vector3f(0, 0, 0));
            return true;
        } else {
            seek(targetPosition);
        }
        return false;
    }

    private void seek(Vector3f targetPosition) {
        PhysicsRigidBody physicsControl = getPhysicsControl();
        final Vector3f desiredVelocity = targetPosition.subtract(physicsControl.getPhysicsLocation());
        desiredVelocity.normalize();
        VectorUtil.scale(desiredVelocity, MAX_VELOCITY);
        final Vector3f steering = desiredVelocity.subtract(physicsControl.getLinearVelocity());
        addSteering(steering);
    }

    private PhysicsRigidBody getPhysicsControl() {
        return this.spatial.getControl(RigidBodyControl.class);
    }


    @Override
    protected void controlUpdate(float tpf) {
        PhysicsRigidBody physicsControl = getPhysicsControl();
        if (this.masterSteering.length() > 0) {
            Vector3f steering = this.masterSteering.add(physicsControl.getLinearVelocity());
            getPhysicsControl().setLinearVelocity(steering);
            this.masterSteering.set(0, 0, 0);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
