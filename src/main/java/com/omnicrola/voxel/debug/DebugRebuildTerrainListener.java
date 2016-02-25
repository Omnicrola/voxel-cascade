package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.WorldLevelState;
import com.omnicrola.voxel.terrain.VoxelTerrainControl;

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
            WorldLevelState currentLevelState = this.voxelGameEngine.getStateManager().getState(WorldLevelState.class);
            LevelState currentLevel = currentLevelState.getCurrentLevel();
            VoxelTerrainControl control = currentLevel.getTerrainNode().getControl(VoxelTerrainControl.class);
            control.forceRebuild();
        }
    }
}
