package com.omnicrola.voxel.fx;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.build.EffectsBuilder;
import com.omnicrola.voxel.entities.commands.IDeathAction;

/**
 * Created by omnic on 1/23/2016.
 */
public class VoxelShowerSpawnAction implements IDeathAction {
    private final int count;
    private EffectsBuilder effectsBuilder;

    public VoxelShowerSpawnAction(EffectsBuilder effectsBuilder, int count) {
        this.effectsBuilder = effectsBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Spatial particles = this.effectsBuilder.buildVoxelSpray(this.count);
        parentSpatial.getParent().attachChild(particles);
        particles.setLocalTranslation(parentSpatial.getWorldTranslation());
    }
}
