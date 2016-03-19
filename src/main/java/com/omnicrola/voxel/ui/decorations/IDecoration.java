package com.omnicrola.voxel.ui.decorations;

import com.jme3.scene.Spatial;

/**
 * Created by Eric on 3/18/2016.
 */
public interface IDecoration {
    void attachTo(Spatial spatial);

    void detatchFrom(Spatial spatial);
}
