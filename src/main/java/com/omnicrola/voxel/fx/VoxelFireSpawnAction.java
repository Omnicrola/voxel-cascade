package com.omnicrola.voxel.fx;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.build.EffectsBuilder;
import com.omnicrola.voxel.entities.commands.IDeathAction;

/**
 * Created by omnic on 1/22/2016.
 */
public class VoxelFireSpawnAction implements IDeathAction {
    private int count;
    private EffectsBuilder effectsBuilder;

    public VoxelFireSpawnAction(EffectsBuilder effectsBuilder, int count) {
        this.effectsBuilder = effectsBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Spatial particles = this.effectsBuilder.buildVoxelFire(1f, this.count);
        Node parent = parentSpatial.getParent();
        parent.attachChild(particles);
        particles.setLocalTranslation(parentSpatial.getLocalTranslation());
    }
}
