package com.omnicrola.voxel.entities;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.world.IGameEntity;

/**
 * Created by Eric on 2/26/2016.
 */
public class Effect implements IGameEntity {
    private Spatial spatial;

    public Effect(Spatial spatial) {
        this.spatial = spatial;
    }

    @Override
    public Spatial getSpatial() {
        return this.spatial;
    }
}
