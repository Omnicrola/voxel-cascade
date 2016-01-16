package com.omnicrola.voxel.jme.wrappers;

import com.jme3.input.FlyByCamera;
import com.jme3.scene.Spatial;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.states.VoxelGameState;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeApplicationWrapper implements IGameContainer {
    private final VoxelGameEngine game;
    private final JmeGuiWrapper guiWrapper;
    private final JmeWorldWrapper worldWrapper;

    public JmeApplicationWrapper(VoxelGameEngine game) {
        this.game = game;
        this.guiWrapper = new JmeGuiWrapper(game);
        this.worldWrapper = new JmeWorldWrapper(game);
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
    public void enableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(true);
    }

    @Override
    public void disableState(Class<? extends VoxelGameState> stateClass) {
        this.game.getStateManager().getState(stateClass).setEnabled(false);
    }
}
