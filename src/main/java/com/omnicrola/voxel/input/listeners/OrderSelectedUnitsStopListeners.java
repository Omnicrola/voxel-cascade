package com.omnicrola.voxel.input.listeners;

import com.jme3.input.controls.ActionListener;
import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;

/**
 * Created by omnic on 1/24/2016.
 */
public class OrderSelectedUnitsStopListeners implements ActionListener {
    private ICurrentLevelProvider currentLevelProvider;

    public OrderSelectedUnitsStopListeners(ICurrentLevelProvider currentLevelProvider) {
        this.currentLevelProvider = currentLevelProvider;
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (!isPressed) {
            this.currentLevelProvider
                    .getCurrentLevel()
                    .getWorldCursor()
                    .getCurrentSelection()
                    .orderStop();
        }
    }
}
