package com.omnicrola.voxel.jme.wrappers;

import com.jme3.input.controls.InputListener;
import com.omnicrola.voxel.engine.VoxelGameEngine;
import com.omnicrola.voxel.engine.input.IActionCode;

/**
 * Created by omnic on 1/15/2016.
 */
public class JmeInputWrapper implements IGameInput {
    private VoxelGameEngine game;

    public JmeInputWrapper(VoxelGameEngine game) {
        this.game = game;
    }

    @Override
    public void bind(IActionCode actionCode, InputListener inputListener) {
        game.getInputManager().addListener(inputListener, actionCode.trigger());
    }

    @Override
    public void unbind(InputListener inputListener) {
        game.getInputManager().removeListener(inputListener);
    }
}
