package com.omnicrola.voxel.engine.input;

/**
 * Created by omnic on 1/15/2016.
 */
public enum GameAction implements IActionCode {
    SELECT;

    @Override
    public String trigger() {
        return this.toString();
    }
}
