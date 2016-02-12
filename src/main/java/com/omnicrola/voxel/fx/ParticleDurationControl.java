package com.omnicrola.voxel.fx;

import com.jme3.effect.ParticleEmitter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/22/2016.
 */
public class ParticleDurationControl extends AbstractControl {
    private float elapsedTime;
    private float maximumTime;
    private ParticleEmitter particleEmitter;

    public ParticleDurationControl(float duration) {
        this.maximumTime = duration;
        this.elapsedTime = 0f;
    }

    @Override
    public void setSpatial(Spatial spatial) {
        super.setSpatial(spatial);
        this.particleEmitter = (ParticleEmitter) spatial;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.elapsedTime += tpf;
        if (this.elapsedTime > this.maximumTime) {
            this.particleEmitter.setParticlesPerSec(0);
            if (this.particleEmitter.getNumVisibleParticles() <= 0) {
                Node parent = particleEmitter.getParent();
                if (parent != null) {
                    parent.detachChild(particleEmitter);
                }
            }
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void resetDuration(float time) {
        this.maximumTime = time;
        this.elapsedTime = 0f;
    }
}
