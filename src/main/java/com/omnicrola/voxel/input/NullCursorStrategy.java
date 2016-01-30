package com.omnicrola.voxel.input;

import com.jme3.cursors.plugins.JmeCursor;

/**
 * Created by omnic on 1/24/2016.
 */
public class NullCursorStrategy implements ICursorStrategy {


    public static final ICursorStrategy INSTANCE = new NullCursorStrategy();

    private JmeCursor cursor2d;

    private NullCursorStrategy() {
        JmeCursor jmeCursor = new JmeCursor();
        this.cursor2d = jmeCursor;
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public JmeCursor get2DCursor() {
        return this.cursor2d;
    }
}
