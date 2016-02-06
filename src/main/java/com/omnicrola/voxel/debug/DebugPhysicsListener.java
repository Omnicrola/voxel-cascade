package com.omnicrola.voxel.debug;

import com.jme3.bullet.BulletAppState;
import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugPhysicsListener implements ActionListener {
    private VoxelGameEngine voxelGameEngine;

    public DebugPhysicsListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            BulletAppState bulletAppState = this.voxelGameEngine.getStateManager().getState(BulletAppState.class);
            bulletAppState.setDebugEnabled(!bulletAppState.isDebugEnabled());
        }
    }
}
