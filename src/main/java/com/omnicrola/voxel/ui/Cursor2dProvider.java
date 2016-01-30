package com.omnicrola.voxel.ui;

import com.jme3.cursors.plugins.JmeCursor;

import java.util.Map;

/**
 * Created by Eric on 1/27/2016.
 */
public class Cursor2dProvider {
    private Map<CursorToken, JmeCursor> cursors;

    public Cursor2dProvider(Map<CursorToken, JmeCursor> cursors) {
        this.cursors = cursors;
    }

    public JmeCursor getCursor(CursorToken token){
        return this.cursors.get(token);
    }
}
