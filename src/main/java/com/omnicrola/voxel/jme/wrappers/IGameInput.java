package com.omnicrola.voxel.jme.wrappers;

import com.jme3.input.controls.InputListener;
import com.omnicrola.voxel.engine.input.IActionCode;
import com.omnicrola.voxel.engine.states.MainMenuState;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameInput {
    void bind(IActionCode actionCode, InputListener keyListener);

    void unbind(InputListener inputListener);
}