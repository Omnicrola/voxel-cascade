package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.input.IUserSelectionObserver;
import com.omnicrola.voxel.input.SelectionGroup;

/**
 * Created by Eric on 1/26/2016.
 */
public class UiCurrentSelectionObserver implements IUserSelectionObserver {
    private ActivePlayScreenController activePlayScreenController;

    public UiCurrentSelectionObserver(ActivePlayScreenController activePlayScreenController) {
        this.activePlayScreenController = activePlayScreenController;
    }

    @Override
    public void notifyNewSelection(SelectionGroup selectionGroup) {
        this.activePlayScreenController.setCurrentSelection(selectionGroup);
    }

    @Override
    public void notifySelectionUpdated(SelectionGroup currentSelection) {
        this.activePlayScreenController.selectionHasUpdated();
    }

}
