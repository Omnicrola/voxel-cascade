package com.omnicrola.voxel.entities.control.move;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.data.units.MovementDefinition;
import com.omnicrola.voxel.entities.control.EntityControlAdapter;
import com.omnicrola.voxel.terrain.ITerrainManager;
import com.omnicrola.voxel.terrain.data.VoxelData;
import com.omnicrola.voxel.world.WorldManager;

import java.util.Optional;

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
        updateGravity(tpf);
        Vector3f newPosition = this.spatial.getWorldTranslation().add(this.currentVelocity.mult(tpf));
        this.spatial.setLocalTranslation(newPosition);
        this.spatial.setLocalRotation(this.rotation);
    }

    private void updateGravity(float tpf) {
        WorldManager worldManager = this.entityControlAdapter.getWorldManager();
        Vector3f location = this.spatial.getWorldTranslation();
        ITerrainManager terrainManager = this.entityControlAdapter.getTerrainManager();
        VoxelData voxelAt = terrainManager.getVoxelAt(location);
        if (voxelAt.isSolid()) {
            Optional<VoxelData> lowestEmptyVoxel = terrainManager.findLowestEmptyVoxel(location);
            if (lowestEmptyVoxel.isPresent()) {
                VoxelData voxelData = lowestEmptyVoxel.get();
                Vector3f newLocation = location.setY(voxelData.getLocation().y);
                this.spatial.setLocalTranslation(newLocation);
            }
            this.fallSpeed.set(0, 0, 0);
        } else {
            float distance = worldManager.distanceToTerrainFloor(location);
            if (distance > 0) {
                this.fallSpeed.addLocal(GRAVITY.mult(tpf));
                Vector3f newLocation = location.add(this.fallSpeed.mult(tpf));
                this.spatial.setLocalTranslation(newLocation);
            } else {
                this.fallSpeed.set(0, 0, 0);
            }
        }
    }

    private void updateCurrentVelocity(float tpf) {
        float maxVelocity = this.movementDefinition.getMaxVelocity() * tpf;
        float maxTurnSpeed = this.movementDefinition.getTurnspeed() * tpf;

        Vector3f currentVelocity = getLocation();
        Vector3f steering = this.desiredVelocity.subtract(currentVelocity);
        Vector3f newVelocity = currentVelocity.add(steering);

        if (newVelocity.length() > 0) {
            this.currentVelocity.set(newVelocity);
            this.rotation.lookAt(newVelocity, Vector3f.UNIT_Y);
        } else {
            this.currentVelocity.set(Vector3f.ZERO);
        }
        this.desiredVelocity.set(0, 0, 0);
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
