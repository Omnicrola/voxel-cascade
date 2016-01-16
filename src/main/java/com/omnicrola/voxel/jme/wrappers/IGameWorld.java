package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameWorld {

    void attach(Spatial node);

    void remove(Spatial node);

    IGeometryBuilder build();

    Vector3f getCameraPosition();
}
