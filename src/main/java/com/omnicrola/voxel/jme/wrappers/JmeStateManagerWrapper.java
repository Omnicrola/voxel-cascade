package com.omnicrola.voxel.jme.wrappers;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;
import com.omnicrola.voxel.engine.VoxelGameEngine;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeStateManagerWrapper implements IStateManager {
    private AppStateManager stateManager;

    public JmeStateManagerWrapper(AppStateManager stateManager) {
        this.stateManager = stateManager;
    }

}
