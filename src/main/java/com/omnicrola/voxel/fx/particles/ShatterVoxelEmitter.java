package com.omnicrola.voxel.fx.particles;

import com.jme3.asset.AssetManager;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;

/**
 * Created by Eric on 4/2/2016.
 */
public class ShatterVoxelEmitter extends VoxelParticleEmitter {
    private boolean hasNotSpawned;

    public ShatterVoxelEmitter(AssetManager assetManager) {
        super(assetManager, "shatter cube", 27);
        setParticleSize(1f / 3f / 2f - 0.05f);
        this.hasNotSpawned = true;
    }

    @Override
    protected void particleUpdate(float tpf) {
        if (hasNotSpawned) {
            this.hasNotSpawned = false;
            buildCubeOfCubes();
        }
    }

    private void buildCubeOfCubes() {
        int index = 0;
        float size = 1f / 3f;
        Vector3f center = new Vector3f(size, size, size);
        for (float x = 0; x < 1f; x += size) {
            for (float y = 0; y < 1f; y += size) {
                for (float z = 0; z < 1f; z += size) {
                    CubeParticle particle = this.particleCubes[index];
                    spawnParticle(particle);
                    Vector3f velocity = createVelocity(center, x, y, z);
                    particle.setLocalTranslation(x, y, z);
                    particle.setVelocity(velocity);
                    index++;
                }
            }
        }
    }

    private Vector3f createVelocity(Vector3f center, float x, float y, float z) {
        float rX = rand();
        float rY = rand();
        float rZ = rand();
        return center.subtract(x, y, z).addLocal(rX, rY, rZ).multLocal(-2f);
    }

    private float rand() {
        return (FastMath.nextRandomFloat() * 1f) - 0.5f;
    }

}
