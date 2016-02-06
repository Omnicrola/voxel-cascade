package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.ActivePlayState;
import com.omnicrola.voxel.jme.wrappers.impl.JmeInputWrapper;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugMouseLookListener implements ActionListener {
    private boolean isLooking;
    private VoxelGameEngine voxelGameEngine;
    private final JmeInputWrapper jmeInputWrapper;

    public DebugMouseLookListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
        this.jmeInputWrapper = new JmeInputWrapper(voxelGameEngine);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.isLooking = !this.isLooking;
            ActivePlayState activePlayState = this.voxelGameEngine.getStateManager().getState(ActivePlayState.class);
            LevelState currentLevelState = activePlayState.getCurrentLevelState();
            currentLevelState.getWorldCursor().setVisible(!this.isLooking);
            this.jmeInputWrapper.setMouseGrabbed(this.isLooking);
        }
    }
}
