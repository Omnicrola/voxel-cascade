package com.omnicrola.voxel.jme.wrappers.impl;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.fx.ParticleDurationControl;
import com.omnicrola.voxel.jme.wrappers.IParticleBuilder;
import com.omnicrola.voxel.settings.GameConstants;

/**
 * Created by omnic on 1/22/2016.
 */
public class ParticleBuilder implements IParticleBuilder {

    private AssetManager assetManager;

    public ParticleBuilder(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    @Override
    public Spatial voxelFire(float duration, int count) {
        ParticleEmitter fire = new ParticleEmitter("particles", ParticleMesh.Type.Triangle, count);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_PARTICLE_SHADER);
        material.setTexture("Texture", assetManager.loadTexture("Textures/test.png"));
        fire.setMaterial(material);
        fire.setImagesX(2);
        fire.setImagesY(2); // 2x2 texture animation
        fire.setEndColor(new ColorRGBA(1f, 0f, 0f, 1f));
        fire.setStartColor(new ColorRGBA(1f, 1f, 0f, 0.5f));
        fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
        fire.setStartSize(0.5f);
        fire.setEndSize(0.05f);
        fire.setGravity(0, 0, 0);
        fire.setLowLife(1f);
        fire.setHighLife(3f);
        fire.getParticleInfluencer().setVelocityVariation(0.3f);
        fire.addControl(new ParticleDurationControl(duration));

        return fire;
    }

    @Override
    public Spatial voxelSpray(int count) {
        ParticleEmitter spray = new ParticleEmitter("spray", ParticleMesh.Type.Point, count);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_PARTICLE_SHADER);
        spray.setMaterial(material);

        spray.setParticlesPerSec(10000);
        spray.addControl(new ParticleDurationControl(0.05f));
        spray.setGravity(0, 9.8f, 0);
        ColorRGBA color = ColorRGBA.randomColor();
        spray.setStartColor(color);
        color.a = 0;

        spray.setStartSize(1f);
        spray.setEndSize(0f);
        spray.setEndColor(color);
        spray.setLowLife(1f);
        spray.setHighLife(3f);
        spray.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 5f, 0));
        spray.getParticleInfluencer().setVelocityVariation(1f);

        return spray;
    }

    @Override
    public Spatial cubicHarvest() {
        int count = 100;
        ParticleEmitter cubicEmitter = new ParticleEmitter("cubic", ParticleMesh.Type.Point, count);
        Material material = new Material(this.assetManager, GameConstants.MATERIAL_PARTICLE_SHADER);
        cubicEmitter.setMaterial(material);

        cubicEmitter.addControl(new ParticleDurationControl(9999));
        cubicEmitter.setParticlesPerSec(100);
        cubicEmitter.setGravity(0, -2, 0);
        cubicEmitter.setStartColor(new ColorRGBA(0f, 1f, 0f, 1f));
        cubicEmitter.setEndColor(new ColorRGBA(0f, 1f, 0f, 0f));

        cubicEmitter.setStartSize(1f);
        cubicEmitter.setEndSize(0f);
        cubicEmitter.setLowLife(1f);
        cubicEmitter.setHighLife(3f);
        cubicEmitter.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2f, 0));
        cubicEmitter.getParticleInfluencer().setVelocityVariation(0.1f);

        return cubicEmitter;
    }
}
