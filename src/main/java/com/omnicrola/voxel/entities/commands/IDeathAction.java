package com.omnicrola.voxel.entities.commands;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/22/2016.
 */
public interface IDeathAction {
    void destruct(Spatial parentSpatial);
}
