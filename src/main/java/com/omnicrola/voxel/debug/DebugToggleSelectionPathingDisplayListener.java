package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;

/**
 * Created by Eric on 3/9/2016.
 */
public class DebugToggleSelectionPathingDisplayListener implements ActionListener {
    private final DebugPathingState debugPathingState;

    public DebugToggleSelectionPathingDisplayListener(DebugPathingState debugPathingState) {
        this.debugPathingState = debugPathingState;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.debugPathingState.setEnabled(!this.debugPathingState.isEnabled());
        }
    }
}
