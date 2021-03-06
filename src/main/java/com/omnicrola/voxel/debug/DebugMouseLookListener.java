package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.input.IWorldCursor;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugMouseLookListener implements ActionListener {
    private boolean isLooking;
    private IWorldCursor worldCursor;
    private final JmeInputWrapper jmeInputWrapper;

    public DebugMouseLookListener(IWorldCursor worldCursor, VoxelGameEngine voxelGameEngine) {
        this.worldCursor = worldCursor;
        this.jmeInputWrapper = new JmeInputWrapper(voxelGameEngine.getInputManager(), voxelGameEngine.getFlyByCamera());
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.isLooking = !this.isLooking;
            this.worldCursor.setVisible(!this.isLooking);
            this.jmeInputWrapper.setMouseGrabbed(this.isLooking);
        }
    }
}
