package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.input.IUserInteractionObserver;
import com.omnicrola.voxel.input.SelectionGroup;

/**
 * Created by omnic on 1/16/2016.
 */
public class UiSelectionObserver implements IUserInteractionObserver {
    private UserInterface userInterface;

    public UiSelectionObserver(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    @Override
    public void notifyNewSelection(SelectionGroup selectionGroup) {
        this.userInterface.setSelectedUnits(selectionGroup);
    }
}
