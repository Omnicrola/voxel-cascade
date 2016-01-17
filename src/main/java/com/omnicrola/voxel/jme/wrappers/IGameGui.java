package com.omnicrola.voxel.jme.wrappers;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameGui {
    GuiBuilder build();

    void attach(Spatial node);

    void remove(Spatial node);

    void setCameraRotation(Quaternion rotation);

    void setCameraPosition(Vector3f vector3f);

}
