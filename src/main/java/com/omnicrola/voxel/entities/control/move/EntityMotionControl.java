package com.omnicrola.voxel.entities.control.move;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.entities.control.unit.GroundVehicleControl;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityMotionControl extends AbstractControl {

    public static final float DAMPENING = 0.0001f;
    public static final Vector3f MAX_VELOCITY = new Vector3f(2, 2, 2);

    private final Vector3f desiredVelocity;
    private MovementDefinition movementDefinition;

    public EntityMotionControl(MovementDefinition movementDefinition) {
        this.movementDefinition = movementDefinition;
        this.desiredVelocity = new Vector3f();
    }

    private void addDesiredVelocity(Vector3f vel) {
        this.desiredVelocity.x += vel.x;
        this.desiredVelocity.y += vel.y;
        this.desiredVelocity.z += vel.z;
    }

    public void moveToward(Vector3f targetPosition) {
        GroundVehicleControl physicsControl = getPhysicsControl();
        Vector3f ourLocation = physicsControl.getPhysicsLocation();
        final Vector3f desiredVelocity = targetPosition
                .subtract(ourLocation)
                .normalize()
                .mult(this.movementDefinition.getMaxVelocity());
        addDesiredVelocity(desiredVelocity);
    }

    @Override
    protected void controlUpdate(float tpf) {
        float maxVelocity = this.movementDefinition.getMaxVelocity() * tpf;
        float maxTurnSpeed = this.movementDefinition.getTurnspeed() * tpf;
        GroundVehicleControl physicsControl = getPhysicsControl();

        Vector3f currentVelocity = physicsControl.getWalkDirection();
        Vector3f steering = this.desiredVelocity.subtract(currentVelocity);
        truncate(steering, maxTurnSpeed);
        Vector3f newVelocity = currentVelocity.add(steering);
        truncate(newVelocity, maxVelocity);

        if (newVelocity.length() > 0) {
            physicsControl.setWalkDirection(newVelocity);
            physicsControl.setViewDirection(newVelocity.setY(0));
        } else {
            physicsControl.setWalkDirection(Vector3f.ZERO);
        }
        this.desiredVelocity.set(0, 0, 0);
    }

    private void truncate(Vector3f vector3f, float max) {
        if (vector3f.length() > max) {
            Vector3f truncated = vector3f.normalize().mult(max);
            vector3f.set(truncated);
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void holdPosition() {
        this.desiredVelocity.set(0, 0, 0);
    }

    public float getPersonalRadius() {
        return this.movementDefinition.getPersonalRadius();
    }

    private GroundVehicleControl getPhysicsControl() {
        return this.spatial.getControl(GroundVehicleControl.class);
    }
}
