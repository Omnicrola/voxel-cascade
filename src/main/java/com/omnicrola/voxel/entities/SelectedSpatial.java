package com.omnicrola.voxel.entities;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.ui.select.ISelectedUnit;

/**
 * Created by Eric on 1/26/2016.
 */
public class SelectedSpatial implements ISelectedUnit{
    private Spatial spatial;

    public SelectedSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    @Override
    public String getDisplayName() {
        return this.spatial.getName();
    }
}
