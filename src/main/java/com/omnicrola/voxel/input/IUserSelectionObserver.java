package com.omnicrola.voxel.input;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IUserSelectionObserver {
    void notifyNewSelection(SelectionGroup selectionGroup);

    void notifySelectionUpdated(SelectionGroup currentSelection);
}
