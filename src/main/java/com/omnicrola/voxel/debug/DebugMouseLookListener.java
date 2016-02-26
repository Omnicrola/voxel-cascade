package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.jme3.renderer.Camera;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.LevelManager;
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
            LevelManager activePlayState = this.voxelGameEngine.getStateManager().getState(LevelManager.class);
            LevelState currentLevelState = activePlayState.getCurrentLevel();
            currentLevelState.getWorldCursor().setVisible(!this.isLooking);
            this.jmeInputWrapper.setMouseGrabbed(this.isLooking);
            Camera camera = this.voxelGameEngine.getCamera();
            System.out.println(camera.getLocation() + "  " + camera.getRotation());
        }
    }
}
