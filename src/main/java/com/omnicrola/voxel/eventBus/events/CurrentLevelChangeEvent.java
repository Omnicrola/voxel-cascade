package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.data.level.LevelState;

/**
 * Created by Eric on 4/6/2016.
 */
public class CurrentLevelChangeEvent {
    private LevelState currentLevelState;

    public CurrentLevelChangeEvent(LevelState currentLevelState) {
        this.currentLevelState = currentLevelState;
    }

    public LevelState getCurrentLevelState() {
        return currentLevelState;
    }
}
