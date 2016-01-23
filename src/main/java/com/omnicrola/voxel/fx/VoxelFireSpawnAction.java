package com.omnicrola.voxel.fx;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.IDeathAction;
import com.omnicrola.voxel.jme.wrappers.IEntityBuilder;

/**
 * Created by omnic on 1/22/2016.
 */
public class VoxelFireSpawnAction implements IDeathAction {
    private IEntityBuilder entityBuilder;

    public VoxelFireSpawnAction(IEntityBuilder entityBuilder, int count) {
        this.entityBuilder = entityBuilder;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Spatial particles = this.entityBuilder.particles().voxelFire(1f);
        Node parent = parentSpatial.getParent();
        parent.attachChild(particles);
        particles.setLocalTranslation(parentSpatial.getLocalTranslation());
    }
}
