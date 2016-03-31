package com.omnicrola.voxel.fx.particles;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

import java.util.Optional;

/**
 * Created by Eric on 3/30/2016.
 */
public class VoxelParticleEmitter extends Node {

    private final CubeParticle[] particleCubes;

    private float lifetime = 1000;
    private float minimumVelocity = 0.1f;
    private float maximumVelocity = 1.0f;
    private float emissionRate = 5f;
    private float velocitySpread = 0.1f;
    private float lifetimeVariation = 100f;
    private Vector3f emissionVector = Vector3f.UNIT_Y;

    public VoxelParticleEmitter(AssetManager assetManager, String name, int count) {
        super(name);
        this.particleCubes = new CubeParticle[count];
        generateParticles(assetManager);
    }

    private void generateParticles(AssetManager assetManager) {
        for (int i = 0; i < this.particleCubes.length; i++) {
            this.particleCubes[i] = new CubeParticle(assetManager);
        }
    }

    @Override
    public void updateLogicalState(float tpf) {
        super.updateLogicalState(tpf);
        int particlesToSpawn = (int) Math.floor(tpf * emissionRate);
        if (particlesToSpawn > 0) {
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

    private void spawnParticle(CubeParticle cubeParticle) {
        this.attachChild(cubeParticle);
        cubeParticle.setLocalTranslation(Vector3f.ZERO);
        float velocityScale = randRange(this.minimumVelocity, this.maximumVelocity);
        float rX = randRange(velocitySpread);
        float rY = randRange(velocitySpread);
        float rZ = randRange(velocitySpread);

        Vector3f velocity = this.emissionVector.mult(velocityScale).add(rX, rY, rZ);
        cubeParticle.setVelocity(velocity);
        float life = this.lifetime + randRange(this.lifetimeVariation);
        cubeParticle.setLifeRemaining(life);
    }

    private float randRange(float bound) {
        return randRange(-bound, bound);
    }

    private float randRange(float min, float max) {
        return (FastMath.rand.nextFloat() * (min - max)) + min;
    }

    private Optional<CubeParticle> getNextInactiveParticle() {
        for (int i = 0; i < this.particleCubes.length; i++) {
            if (this.particleCubes[i].isInactive()) {
                return Optional.of(this.particleCubes[i]);
            }
        }
        return Optional.empty();
    }

}
