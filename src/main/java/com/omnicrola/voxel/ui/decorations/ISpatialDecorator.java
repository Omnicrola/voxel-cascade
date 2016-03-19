package com.omnicrola.voxel.ui.decorations;

import com.omnicrola.voxel.ui.select.ISelectedUnit;

/**
 * Created by Eric on 3/18/2016.
 */
public interface ISpatialDecorator {
    void addHealthbar(ISelectedUnit selectedUnit);

    void removeHealthbar(ISelectedUnit selectedUnit);
}
