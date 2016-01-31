package com.omnicrola.voxel.fx;

import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.commands.IDeathAction;
import com.omnicrola.voxel.jme.wrappers.IWorldBuilder;

/**
 * Created by omnic on 1/22/2016.
 */
public class VoxelFireSpawnAction implements IDeathAction {
    private IWorldBuilder entityBuilder;
    private int count;

    public VoxelFireSpawnAction(IWorldBuilder entityBuilder, int count) {
        this.entityBuilder = entityBuilder;
        this.count = count;
    }

    @Override
    public void destruct(Spatial parentSpatial) {
        Spatial particles = this.entityBuilder.particles().voxelFire(1f, this.count);
        Node parent = parentSpatial.getParent();
        parent.attachChild(particles);
        particles.setLocalTranslation(parentSpatial.getLocalTranslation());
    }
}
