package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.data.LevelManager;
import com.omnicrola.voxel.engine.states.WorldManagerState;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugReloadLevelListener implements ActionListener {
    private VoxelGameEngine voxelGameEngine;

    public DebugReloadLevelListener(VoxelGameEngine voxelGameEngine) {
        this.voxelGameEngine = voxelGameEngine;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            LevelManager currentLevelState = voxelGameEngine.getStateManager().getState(WorldManagerState.class).getLevelManager();
            LevelDefinition basicLevelDefinition = LevelGeneratorTool.createBasicLevelDefinition();
            currentLevelState.loadLevel(basicLevelDefinition);
        }
    }
}
