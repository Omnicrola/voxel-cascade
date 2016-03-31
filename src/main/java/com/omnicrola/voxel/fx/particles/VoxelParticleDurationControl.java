package com.omnicrola.voxel.fx.particles;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

/**
 * Created by omnic on 1/22/2016.
 */
public class VoxelParticleDurationControl extends AbstractControl {
    private float elapsedTime;
    private float maximumTime;
    private VoxelParticleEmitter voxelParticleEmitter;

    public VoxelParticleDurationControl(float duration, VoxelParticleEmitter voxelParticleEmitter) {
        this.maximumTime = duration;
        this.voxelParticleEmitter = voxelParticleEmitter;
        this.elapsedTime = 0f;
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.elapsedTime += tpf;
        if (this.elapsedTime > this.maximumTime) {
            this.voxelParticleEmitter.setEmissionRate(0);
            if (this.voxelParticleEmitter.getActiveParticleCount() <= 0) {
                this.voxelParticleEmitter.removeFromParent();
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
