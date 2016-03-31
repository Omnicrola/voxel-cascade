package com.omnicrola.voxel.fx;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;

/**
 * Created by omnic on 1/22/2016.
 */
public class VoxelFireSpawnAction implements IDeathAction {
    private int count;
    private ParticleBuilder effectsBuilder;

    public VoxelFireSpawnAction(ParticleBuilder effectsBuilder, int count) {
        this.effectsBuilder = effectsBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Effect particles = this.effectsBuilder.voxelFire(1f, this.count);
        particles.setLocation(parentSpatial.getLocalTranslation());
    }
}
