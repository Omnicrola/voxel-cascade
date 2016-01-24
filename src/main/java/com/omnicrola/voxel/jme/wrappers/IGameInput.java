package com.omnicrola.voxel.jme.wrappers;

import com.jme3.input.controls.InputListener;
import com.jme3.math.Vector2f;
import com.omnicrola.voxel.input.IActionCode;

/**
 * Created by omnic on 1/15/2016.
 */
public interface IGameInput {
    void bind(IActionCode actionCode, InputListener keyListener);

    void unbind(InputListener inputListener);

    void toggleMouseGrabbed();

    void setMouseGrabbed(boolean isGrabbed);

    void setCameraMoveSpeed(float speed);

    Vector2f getCursorPosition();

}
