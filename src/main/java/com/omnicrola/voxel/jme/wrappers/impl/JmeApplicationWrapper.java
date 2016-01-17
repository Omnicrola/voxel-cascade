package com.omnicrola.voxel.jme.wrappers.impl;

import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelGameState;
import com.omnicrola.voxel.jme.wrappers.*;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeApplicationWrapper implements IGameContainer {
    private final VoxelGameEngine game;
    private final JmeGuiWrapper guiWrapper;
    private final JmeWorldWrapper worldWrapper;
    private final JmeInputWrapper inputWrapper;
    private final JmePhysicsWrapper physicsWrapper;

    public JmeApplicationWrapper(VoxelGameEngine game) {
        this.game = game;
        this.guiWrapper = new JmeGuiWrapper(game);
        this.worldWrapper = new JmeWorldWrapper(game);
        this.inputWrapper = new JmeInputWrapper(game);
        this.physicsWrapper = new JmePhysicsWrapper(game);
    }

    @Override
    public IGameGui gui() {
        return this.guiWrapper;
    }

    @Override
    public IGameWorld world() {
        return this.worldWrapper;
    }

    @Override
    public IGameInput input() {
        return this.inputWrapper;
    }

    @Override
    public IGamePhysics physics() {
        return this.physicsWrapper;
    }

    @Override
    public void enableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(true);
    }

    @Override
    public void disableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(false);
    }
}
