package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.GameMouseEvent;
import com.omnicrola.voxel.input.IWorldCursor;

/**
 * Created by omnic on 1/24/2016.
 */
public class ExecutePrimaryCursorListener implements ActionListener {
    private boolean isMultiSelecting;
    private IWorldCursor worldCursor;

    public ExecutePrimaryCursorListener(IWorldCursor worldCursor) {
        this.worldCursor = worldCursor;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name.equals(GameInputAction.MULTI_SELECT.toString())) {
            this.isMultiSelecting = isPressed;
        } else if (name.equals(GameInputAction.MOUSE_PRIMARY.toString())) {
                GameMouseEvent gameMouseEvent = new GameMouseEvent(isPressed, isMultiSelecting);
                worldCursor.executePrimary(gameMouseEvent);
        }
    }
}
