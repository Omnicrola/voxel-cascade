package com.omnicrola.voxel.jme.wrappers;

import com.omnicrola.voxel.entities.Effect;

/**
 * Created by omnic on 1/22/2016.
 */
public interface IParticleBuilder {

    Effect voxelFire(float duration, int count);

    Effect voxelSpray(int count);

    Effect cubicHarvest();
}
