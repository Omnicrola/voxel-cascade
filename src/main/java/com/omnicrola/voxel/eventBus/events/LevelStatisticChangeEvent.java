package com.omnicrola.voxel.eventBus.events;

import com.omnicrola.voxel.data.TeamData;

/**
 * Created by Eric on 4/5/2016.
 */
public class LevelStatisticChangeEvent {
    private TeamData playerTeam;

    public TeamData getPlayerTeam() {
        return playerTeam;
    }

    public float getResources(TeamData team) {
        return 0;
    }
}
