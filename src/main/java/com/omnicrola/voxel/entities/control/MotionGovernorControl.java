package com.omnicrola.voxel.entities.control;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.physics.GroundVehicleControl;

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

    public void moveToward(Vector3f targetPosition) {
        GroundVehicleControl physicsControl = getPhysicsControl();
        final Vector3f desiredVelocity = targetPosition.subtract(physicsControl.getPhysicsLocation());
        final Vector3f steering = desiredVelocity.subtract(physicsControl.getLinearVelocity());
        addSteering(steering);
    }

    private GroundVehicleControl getPhysicsControl() {
        return this.spatial.getControl(GroundVehicleControl.class);
    }

    @Override
    protected void controlUpdate(float tpf) {
        GroundVehicleControl physicsControl = getPhysicsControl();
        Vector3f steering = this.masterSteering
                .normalize()
                .mult(this.movementDefinition.getMaxVelocity());
        physicsControl.setLinearVelocity(steering.mult(4));
        if (steering.length() > 0) {
//            physicsControl.setViewDirection(steering.setY(0).normalize().mult(0.5f));
        }
        this.masterSteering.set(0, 0, 0);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void holdPosition() {
        this.masterSteering.set(0, 0, 0);
    }

    public float getPersonalRadius() {
        return this.movementDefinition.getPersonalRadius();
    }
}
