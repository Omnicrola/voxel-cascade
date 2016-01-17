package com.omnicrola.voxel.input;

import com.jme3.scene.Geometry;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IUserInteractionObserver {
    void notifyNewSelection(Geometry geometry);
}
