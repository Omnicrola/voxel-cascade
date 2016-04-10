package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.data.level.LevelDefinition;

import java.util.List;

/**
 * Created by Eric on 4/10/2016.
 */
public class UpdateAvailableLevelsEvent {
    private List<LevelDefinition> levels;

    public UpdateAvailableLevelsEvent(List<LevelDefinition> levels) {
        this.levels = levels;
    }

    public List<LevelDefinition> getLevels() {
        return levels;
    }
}
