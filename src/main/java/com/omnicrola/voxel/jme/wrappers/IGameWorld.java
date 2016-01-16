package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.input.WorldCursor;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameWorld {

    void attach(Spatial node);

    void remove(Spatial node);

    IGeometryBuilder build();

    Vector3f getCameraPosition();

    WorldCursor createCursor(Node terrain);
}
