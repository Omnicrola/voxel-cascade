package com.omnicrola.voxel.jme.wrappers;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameWorld {

    void attachToWorld(Spatial node);

    void removeFromWorld(Spatial node);

}
