package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/24/2016.
 */
public class NullCursorStrategy implements ICursorStrategy {

    public static final NullCursorStrategy NULL = new NullCursorStrategy();

    private NullCursorStrategy() {
    }

    @Override
    public void executePrimary(boolean isPressed, SelectionGroup currentSelection) {

    }

    @Override
    public void executeSecondary(boolean isPressed, SelectionGroup currentSelection) {

    }
}
