package com.omnicrola.voxel.jme.wrappers;

import com.jme3.asset.AssetManager;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.ui.GLabel;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameContainer {

    void attachToWorld(Spatial node);

    void removeFromWorld(Spatial node);

    GuiBuilder guiBuilder();

    void attachToGui(Spatial node);

    void removeFromGui(Spatial node);

    void setMouseGrabbed(boolean isGrabbed);
}
