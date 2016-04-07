package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.input.SelectionGroup;

/**
 * Created by Eric on 4/5/2016.
 */
public class SelectionChangeEvent {
    public SelectionChangeEvent(SelectionGroup selection) {
        this.selection = selection;
    }

    private SelectionGroup selection;

    public SelectionGroup getSelection() {
        return selection;
    }
}
