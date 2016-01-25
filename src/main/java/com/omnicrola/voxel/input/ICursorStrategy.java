package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/24/2016.
 */
public interface ICursorStrategy {
    void executePrimary(boolean isPressed, SelectionGroup currentSelection);

    void executeSecondary(boolean isPressed, SelectionGroup currentSelection);
}
