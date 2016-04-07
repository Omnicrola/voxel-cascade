package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.data.TeamData;
import com.omnicrola.voxel.data.level.LevelState;

/**
 * Created by Eric on 4/5/2016.
 */
public class LevelStatisticChangeEvent {
    private LevelState levelState;

    public LevelStatisticChangeEvent(LevelState levelState) {
        this.levelState = levelState;
    }

    public TeamData getPlayerTeam() {
        return levelState.getPlayerTeam();
    }

    public float getResources(TeamData team) {
        return levelState.getResources(team);
    }
}
