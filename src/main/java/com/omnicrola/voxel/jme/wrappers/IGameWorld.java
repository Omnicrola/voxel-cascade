package com.omnicrola.voxel.jme.wrappers;

import com.jme3.light.Light;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.input.WorldCursor;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameWorld {

    void attach(Spatial node);

    void remove(Spatial node);

    IEntityBuilder build();

    WorldCursor createCursor(Node terrain);

    void addLight(Light light);
}
