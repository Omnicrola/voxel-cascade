package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.input.SelectionGroup;

/**
 * Created by Eric on 1/24/2016.
 */
public interface IGameUserInterface {
    boolean isInBuildMode();

    void setBuildMode(boolean buildMode);

    void setSelectedUnits(SelectionGroup selectionGroup);
}
