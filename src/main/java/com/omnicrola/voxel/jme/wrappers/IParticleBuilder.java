package com.omnicrola.voxel.jme.wrappers;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/22/2016.
 */
public interface IParticleBuilder {

    Spatial voxelFire(float duration, int count);

    Spatial voxelSpray(int count);

    Spatial cubicHarvest();
}
