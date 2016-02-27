package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelTerrainState;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugRebuildTerrainListener implements ActionListener{
    private VoxelGameEngine voxelGameEngine;

    public DebugRebuildTerrainListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            VoxelTerrainState terrainState = this.voxelGameEngine.getStateManager().getState(VoxelTerrainState.class);
            terrainState.globalRebuild();

        }
    }
}
