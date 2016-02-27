package com.omnicrola.voxel.entities;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.fx.ParticleDurationControl;

/**
 * Created by Eric on 2/26/2016.
 */
public class Effect extends AbstractGameEntity{

    public Effect(Spatial spatial) {
        super(spatial);
    }

    public void resetDuration(float duration) {
        ParticleDurationControl durationControl = this.spatial.getControl(ParticleDurationControl.class);
        durationControl.resetDuration(duration);

    }
}
