package com.omnicrola.voxel.engine.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.jme.wrappers.*;

/**
 * Created by omnic on 1/15/2016.
 */
public abstract class VoxelGameState extends AbstractAppState {


    private JmeApplicationWrapper jmeApplicationWrapper;

    public VoxelGameState() {

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.jmeApplicationWrapper = new JmeApplicationWrapper((VoxelGameEngine) app);
        voxelInitialize(this.jmeApplicationWrapper);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            voxelEnable(this.jmeApplicationWrapper);
        } else {
            voxelDisable(this.jmeApplicationWrapper);
        }
    }

    @Override
    final public void stateAttached(AppStateManager stateManager) {
    }


    @Override
    final public void stateDetached(AppStateManager stateManager) {
    }


    protected abstract void voxelInitialize(IGameContainer gameContainer);

    protected abstract void voxelEnable(IGameContainer gameContainer);

    protected abstract void voxelDisable(IGameContainer gameContainer);

}
