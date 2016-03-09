package com.omnicrola.voxel.entities.control.move;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.terrain.ITerrainManager;

/**
 * Created by omnic on 1/16/2016.
 */
public class EntityMotionControl extends AbstractControl {

    public static final float DAMPENING = 0.0001f;
    public static final Vector3f MAX_VELOCITY = new Vector3f(2, 2, 2);
    private static final Vector3f GRAVITY = new Vector3f(0, -9.8f, 0);

    private final Vector3f desiredVelocity;
    private final Quaternion rotation;
    private MovementDefinition movementDefinition;
    private EntityControlAdapter entityControlAdapter;
    private boolean isStopping;
    private Vector3f currentVelocity;
    private Vector3f fallSpeed;

    public EntityMotionControl(MovementDefinition movementDefinition, EntityControlAdapter entityControlAdapter) {
        this.movementDefinition = movementDefinition;
        this.entityControlAdapter = entityControlAdapter;
        this.desiredVelocity = new Vector3f();
        this.currentVelocity = new Vector3f();
        this.fallSpeed = new Vector3f();
        this.rotation = new Quaternion();
    }

    private void addDesiredVelocity(Vector3f vel) {
        this.desiredVelocity.x += vel.x;
        this.desiredVelocity.y += vel.y;
        this.desiredVelocity.z += vel.z;
    }

    public void moveToward(Vector3f targetPosition) {
        Vector3f ourLocation = getLocation();
        final Vector3f desiredVelocity = targetPosition
                .subtract(ourLocation)
                .normalize()
                .mult(this.movementDefinition.getMaxVelocity());
        addDesiredVelocity(desiredVelocity);
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (isStopping) {
            this.desiredVelocity.set(0, 0, 0);
            this.currentVelocity.set(this.desiredVelocity);
            this.isStopping = false;
            return;
        }
        updateCurrentVelocity(tpf);
        updateGravity();
        Vector3f newPosition = this.spatial.getWorldTranslation().add(this.currentVelocity.mult(tpf));
        this.spatial.setLocalTranslation(newPosition);
        this.spatial.setLocalRotation(this.rotation);
    }

    private void updateGravity() {
        ITerrainManager terrainManager = this.entityControlAdapter.getTerrainManager();
        boolean belowTerrain = terrainManager.isBelowTerrain((Geometry) this.spatial);
        if (belowTerrain) {
            this.fallSpeed.add(GRAVITY);
            Vector3f newPosition = this.spatial.getWorldTranslation().add(this.fallSpeed);
            this.spatial.setLocalTranslation(newPosition);
        } else {
            this.fallSpeed.set(0,0,0);
        }
    }

    private void updateCurrentVelocity(float tpf) {
        float maxVelocity = this.movementDefinition.getMaxVelocity() * tpf;
        float maxTurnSpeed = this.movementDefinition.getTurnspeed() * tpf;

        Vector3f currentVelocity = getLocation();
        Vector3f steering = this.desiredVelocity.subtract(currentVelocity);
        truncate(steering, maxTurnSpeed);
        Vector3f newVelocity = currentVelocity.add(steering);
        truncate(newVelocity, maxVelocity);

        if (newVelocity.length() > 0) {
            this.currentVelocity.set(newVelocity);
            this.rotation.lookAt(newVelocity, Vector3f.UNIT_Y);
        } else {
            this.currentVelocity.set(Vector3f.ZERO);
        }
        this.desiredVelocity.set(0, 0, 0);
    }

    private void truncate(Vector3f vector3f, float max) {
//        if (vector3f.length() > max) {
//            Vector3f truncated = vector3f.normalize().mult(max);
//            vector3f.set(truncated);
//        }
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

    public void stop() {
        this.isStopping = true;
    }

    public Vector3f getLocation() {
        return this.spatial.getWorldTranslation();
    }
}
