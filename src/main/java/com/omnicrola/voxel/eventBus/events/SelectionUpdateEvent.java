package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.ui.select.ISelectedUnit;

import java.util.List;

/**
 * Created by Eric on 4/6/2016.
 */
public class SelectionUpdateEvent {
    private List<ISelectedUnit> removedUnits;

    public SelectionUpdateEvent(List<ISelectedUnit> removedUnits) {
        this.removedUnits = removedUnits;
    }

    public List<ISelectedUnit> getRemovedUnits() {
        return removedUnits;
    }
}
