package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.data.ILevelObserver;

/**
 * Created by omnic on 2/11/2016.
 */
public class UiLevelObserver implements ILevelObserver {
    private ActivePlayScreenController activePlayScreenController;

    public UiLevelObserver(ActivePlayScreenController activePlayScreenController) {
        this.activePlayScreenController = activePlayScreenController;
    }

    @Override
    public void levelChanged() {
        this.activePlayScreenController.updateStats();
    }
}
