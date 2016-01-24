package com.omnicrola.voxel.fx;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.IDeathAction;
import com.omnicrola.voxel.jme.wrappers.IEntityBuilder;

/**
 * Created by omnic on 1/23/2016.
 */
public class VoxelShowerSpawnAction implements IDeathAction {
    private final IEntityBuilder entityBuilder;
    private final int count;

    public VoxelShowerSpawnAction(IEntityBuilder entityBuilder, int count) {
        this.entityBuilder = entityBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Spatial particles = this.entityBuilder.particles().voxelSpray(this.count);
        parentSpatial.getParent().attachChild(particles);
        particles.setLocalTranslation(parentSpatial.getWorldTranslation());
    }
}
