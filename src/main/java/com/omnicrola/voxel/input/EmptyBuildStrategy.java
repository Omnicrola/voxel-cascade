package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 * Created by Eric on 1/30/2016.
 */
public class EmptyBuildStrategy implements ICursorStrategy {
    private final Node empty3dCursor;
    private JmeCursor buildCursor;

    public EmptyBuildStrategy(JmeCursor buildCursor) {
        this.buildCursor = buildCursor;
        this.empty3dCursor = new Node();
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public JmeCursor get2DCursor() {
        return this.buildCursor;
    }

    @Override
    public Spatial get3dCursor() {
        return this.empty3dCursor;
    }
}
