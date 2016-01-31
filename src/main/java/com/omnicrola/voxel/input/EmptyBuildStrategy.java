package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;

/**
 * Created by Eric on 1/30/2016.
 */
public class EmptyBuildStrategy implements ICursorStrategy {
    private JmeCursor buildCursor;

    public EmptyBuildStrategy(JmeCursor buildCursor) {
        this.buildCursor = buildCursor;
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
}
