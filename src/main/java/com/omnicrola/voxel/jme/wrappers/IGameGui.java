package com.omnicrola.voxel.jme.wrappers;

import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameGui {
    GuiBuilder build();

    void attach(Spatial node);

    void remove(Spatial node);

    void setMouseGrabbed(boolean isGrabbed);

    void toggleMouseGrabbed();
}
