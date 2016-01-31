package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.scene.Spatial;

/**
 * Created by omnic on 1/24/2016.
 */
public interface ICursorStrategy {
    void executePrimary(GameMouseEvent isPressed, SelectionGroup currentSelection);

    void executeSecondary(boolean isPressed, SelectionGroup currentSelection);

    JmeCursor get2DCursor();

    Spatial get3dCursor();
}
