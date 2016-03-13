package com.omnicrola.voxel.input.actions;

import com.jme3.math.Vector3f;

/**
 * Created by omnic on 3/12/2016.
 */
public interface IBuildCompletionStrategy {
    void build(Vector3f location);

    boolean isAbleToBuild();
}
