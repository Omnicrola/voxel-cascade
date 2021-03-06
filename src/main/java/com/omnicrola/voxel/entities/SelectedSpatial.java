package com.omnicrola.voxel.entities;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.ui.decorations.IDecoration;
import com.omnicrola.voxel.ui.select.ISelectedUnit;

/**
 * Created by Eric on 1/26/2016.
 */
public class SelectedSpatial implements ISelectedUnit {
    private Spatial spatial;

    public SelectedSpatial(Spatial spatial) {
        this.spatial = spatial;
    }

    @Override
    public String getDisplayName() {
        return this.spatial.getName();
    }

    @Override
    public void addDecoration(IDecoration decoration) {
        decoration.attachTo(this.spatial);
    }

    @Override
    public void removeDecoration(IDecoration decoration) {
        decoration.detatchFrom(this.spatial);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SelectedSpatial)) return false;

        SelectedSpatial that = (SelectedSpatial) o;

        return spatial.equals(that.spatial);

    }

    @Override
    public int hashCode() {
        return spatial.hashCode();
    }
}
