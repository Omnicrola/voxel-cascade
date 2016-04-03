package com.omnicrola.voxel.fx.particles;

import com.jme3.asset.AssetManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.Optional;

/**
 * Created by Eric on 3/30/2016.
 */
public class VoxelParticleEmitter extends Node {

    public final CubeParticle[] particleCubes;

    private boolean isInitialized;
    private float timeSinceLastSpawn;
    private float lifetime = 1f;
    private float lifetimeVariation = 1f;

    private float minimumVelocity = 10f;
    private float maximumVelocity = 20.0f;
    private float velocitySpread = 0.1f;

    private float emissionRate = 5f;
    private Vector3f emissionVector = Vector3f.UNIT_Y;
    private float gravity = -9.98f;
    private boolean useFloor = false;
    private float floor = 1.0f;
    private float bounceDampening = 0.1f;
    private float particleSize = 0.05f;

    private AssetManager assetManager;
    private ColorRGBA color = ColorRGBA.White;

    public VoxelParticleEmitter(AssetManager assetManager, String name, int count) {
        super(name);
        this.assetManager = assetManager;
        this.particleCubes = new CubeParticle[count];
        this.isInitialized = false;
        this.timeSinceLastSpawn = 0f;
    }

    public int getActiveParticleCount() {
        int count = 0;
        for (int i = 0; i < this.particleCubes.length; i++) {
            CubeParticle particle = this.particleCubes[i];
            if (particle != null && particle.isActive()) {
                count++;
            }
        }
        return count;
    }

    public void setParticleSize(float particleSize) {
        this.particleSize = particleSize;
    }

    public void setEmissionRate(float emissionRate) {
        this.emissionRate = emissionRate;
    }

    public void setMaximumVelocity(float maximumVelocity) {
        this.maximumVelocity = maximumVelocity;
    }

    public void setMinimumVelocity(float minimumVelocity) {
        this.minimumVelocity = minimumVelocity;
    }

    public void setColor(ColorRGBA color) {
        this.color = color;
    }

    public void setLifetime(float lifetime) {
        this.lifetime = lifetime;
    }

    public void setLifetimeVariation(float lifetimeVariation) {
        this.lifetimeVariation = lifetimeVariation;
    }

    public void setVelocitySpread(float velocitySpread) {
        this.velocitySpread = velocitySpread;
    }

    public void setEmissionVector(Vector3f emissionVector) {
        this.emissionVector = emissionVector;
    }

    public void setFloor(float newFloor) {
        this.floor = newFloor;
    }

    public void setUseFloor(boolean useFloor) {
        this.useFloor = useFloor;
    }

    private void generateParticles() {
        for (int i = 0; i < this.particleCubes.length; i++) {
            this.particleCubes[i] = new CubeParticle(assetManager, this.particleSize);
        }
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        if (!isInitialized) {
            this.isInitialized = true;
            generateParticles();
        }
        particleUpdate(tpf);
    }

    protected void particleUpdate(float tpf) {
        this.timeSinceLastSpawn += tpf;
        int particlesToSpawn = (int) Math.floor(timeSinceLastSpawn * emissionRate);
        if (particlesToSpawn > 0) {
            this.timeSinceLastSpawn = 0f;
            emitParticles(particlesToSpawn);
        }
    }

    private void emitParticles(int particlesToSpawn) {
        for (int i = 0; i < particlesToSpawn; i++) {
            Optional<CubeParticle> nextInactiveParticle = getNextInactiveParticle();
            if (nextInactiveParticle.isPresent()) {
                spawnParticle(nextInactiveParticle.get());
            }
        }
    }

    protected void spawnParticle(CubeParticle cubeParticle) {
        this.attachChild(cubeParticle);
        cubeParticle.setLocalTranslation(Vector3f.ZERO);
        float velocityScale = randRange(this.minimumVelocity, this.maximumVelocity);
        float rX = randRange(velocitySpread);
        float rY = randRange(velocitySpread);
        float rZ = randRange(velocitySpread);

        Vector3f velocity = this.emissionVector.mult(velocityScale).add(rX, rY, rZ);
        cubeParticle.setVelocity(velocity);
        float life = this.lifetime + randRange(this.lifetimeVariation);
        cubeParticle.setLife(life);
        cubeParticle.setColor(this.color);
        cubeParticle.setGravity(this.gravity);
        cubeParticle.setFloor(this.floor);
        cubeParticle.setBounceDampening(this.bounceDampening);
        cubeParticle.useFloor(this.useFloor);
    }

    private float randRange(float bound) {
        return randRange(-bound, bound);
    }

    private float randRange(float min, float max) {
        return (FastMath.rand.nextFloat() * (max - min)) + min;
    }

    private Optional<CubeParticle> getNextInactiveParticle() {
        for (int i = 0; i < this.particleCubes.length; i++) {
            if (this.particleCubes[i].isInactive()) {
                return Optional.of(this.particleCubes[i]);
            }
        }
        return Optional.empty();
    }

    public void setBounceDampening(float bounceDampening) {
        this.bounceDampening = bounceDampening;
    }
}
