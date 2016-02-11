package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.input.IUserInteractionObserver;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.ui.controllers.ActivePlayScreenController;

/**
 * Created by Eric on 1/26/2016.
 */
public class UiCurrentSelectionObserver implements IUserInteractionObserver {
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
