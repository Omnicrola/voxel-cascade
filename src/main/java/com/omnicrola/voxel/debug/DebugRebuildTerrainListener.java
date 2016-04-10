package com.omnicrola.voxel.debug;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.terrain.ITerrainManager;

/**
 * Created by Eric on 2/5/2016.
 */
public class DebugRebuildTerrainListener implements ActionListener{
    private ITerrainManager terrainManager;

    public DebugRebuildTerrainListener(ITerrainManager terrainManager) {
        this.terrainManager = terrainManager;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            terrainManager.globalRebuild();

        }
    }
}
