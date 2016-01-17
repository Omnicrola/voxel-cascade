package com.omnicrola.voxel.ui;

import com.jme3.scene.Geometry;
import com.omnicrola.voxel.engine.input.IUserInteractionObserver;

/**
 * Created by omnic on 1/16/2016.
 */
public class UiSelectionObserver implements IUserInteractionObserver {
    private UserInterface userInterface;

    public UiSelectionObserver(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void notifyNewSelection(Geometry geometry) {
        this.userInterface.setSelectedEntity(geometry);
    }
}
