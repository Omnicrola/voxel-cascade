package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.data.level.LevelDefinition;

/**
 * Created by Eric on 4/10/2016.
 */
public class MultiplayerLobbyMapEvent {
    private LevelDefinition level;

    public MultiplayerLobbyMapEvent(LevelDefinition level) {
        this.level = level;
    }

    public LevelDefinition getLevel() {
        return level;
    }
}
