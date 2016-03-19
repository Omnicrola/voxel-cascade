package com.omnicrola.voxel.ui.select;

import com.omnicrola.voxel.ui.decorations.IDecoration;

/**
 * Created by Eric on 1/26/2016.
 */
public interface ISelectedUnit {
    String getDisplayName();

    void addDecoration(IDecoration decoration);

    void removeDecoration(IDecoration decoration);
}
