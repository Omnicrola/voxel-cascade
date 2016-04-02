package com.omnicrola.voxel.fx;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.Effect;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.jme.wrappers.impl.ParticleBuilder;

/**
 * Created by omnic on 1/23/2016.
 */
public class VoxelShowerSpawnAction implements IDeathAction {
    private final int count;
    private ParticleBuilder effectsBuilder;

    public VoxelShowerSpawnAction(ParticleBuilder effectsBuilder, int count) {
        this.effectsBuilder = effectsBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
//        Effect particles = this.effectsBuilder.voxelSpray(this.count);
        Effect particles = this.effectsBuilder.cubicShower(20, 0f);
        particles.setLocation(parentSpatial.getLocalTranslation());
    }
}
