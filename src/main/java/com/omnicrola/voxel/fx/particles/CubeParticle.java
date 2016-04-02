package com.omnicrola.voxel.fx.particles;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by Eric on 3/30/2016.
 */
public class CubeParticle extends Geometry {
    public static final float SIZE = 0.05f;
    public static final float MILLISECONDS_IN_ONE_SECOND = 1000f;
    private final Vector3f velocity;
    private float lifeRemaining;
    private float gravity;
    private float floor;
    private boolean useFloor;
    private float bounciness;

    public CubeParticle(AssetManager assetManager) {
        this.velocity = new Vector3f();
        this.floor = 0;
        this.lifeRemaining = 0f;
        this.mesh = new Box(SIZE, SIZE, SIZE);
        this.material = new Material(assetManager, GameConstants.MATERIAL_UNSHADED);
        setColor(ColorRGBA.White);
    }

    public void setColor(ColorRGBA color) {
        this.material.setColor("Color", color);
    }


    public void setVelocity(Vector3f newVelocity) {
        this.velocity.set(newVelocity);
    }

    public void setLifeRemaining(float lifeRemaining) {
        this.lifeRemaining = lifeRemaining;
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        updatePosition(tpf);
        checkIfParticleHasExpired(tpf);
    }

    private void updatePosition(float tpf) {
        this.velocity.addLocal(0, this.gravity * tpf, 0);
        Vector3f newPosition = this.getLocalTranslation().add(this.velocity.mult(tpf));
        if (this.useFloor &&
                newPosition.y <= this.floor) {
            newPosition.y = this.floor;
            this.velocity.multLocal(this.bounciness);
            this.velocity.y = this.velocity.y * -1;
        }
        this.setLocalTranslation(newPosition);
    }


    private void checkIfParticleHasExpired(float tpf) {
        this.lifeRemaining -= tpf ;
        if (this.lifeRemaining <= 0) {
            this.removeFromParent();
        }
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public boolean isActive() {
        return this.parent != null;
    }

    public boolean isInactive() {
        return !isActive();
    }

    public void setFloor(float floor) {
        this.floor = floor;
    }

    public void useFloor(boolean useFloor) {
        this.useFloor = useFloor;
    }

    public void setBounciness(float bounciness) {
        this.bounciness = bounciness;
    }
}
