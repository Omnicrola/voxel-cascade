package com.omnicrola.voxel.input;

import com.omnicrola.voxel.ui.select.ISelectedUnit;

import java.util.List;

/**
 * Created by omnic on 1/16/2016.
 */
public interface IUserSelectionObserver {
    void notifyNewSelection(SelectionGroup selectionGroup);

    void notifySelectionUpdated(SelectionGroup currentSelection, List<ISelectedUnit> removedUnits);

}
