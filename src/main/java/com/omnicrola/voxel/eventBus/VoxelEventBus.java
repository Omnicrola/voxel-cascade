package com.omnicrola.voxel.eventBus;

import com.google.common.eventbus.EventBus;

/**
 * Created by omnic on 4/4/2016.
 */
public class VoxelEventBus {
    private static final EventBus eventBus = new EventBus();

    public static EventBus INSTANCE() {
        return eventBus;
    }
}
